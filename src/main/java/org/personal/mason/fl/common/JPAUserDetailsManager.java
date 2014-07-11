package org.personal.mason.fl.common;

import org.personal.mason.fl.domain.model.Group;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.GroupRepository;
import org.personal.mason.fl.domain.repository.RoleRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mason on 6/30/14.
 */

@Service
public class JPAUserDetailsManager extends JPAUserDetailsService implements UserDetailsManager, GroupManager, RoleManager {

	private final Logger logger = LoggerFactory.getLogger(JPAUserDetailsManager.class);
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean enableEncodePassword = true;
    
    private AuthenticationManager authenticationManager;

    private UserCache userCache = new NullUserCache();
    private JpaUserDetailsDefaults jpaUserDetailsDefaults = new JpaUserDetailsDefaults.NullJpaUserDetailsDefaults();

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJpaUserDetailsDefaults(JpaUserDetailsDefaults jpaUserDetailsDefaults) {
        this.jpaUserDetailsDefaults = jpaUserDetailsDefaults;
    }

    //~ GroupManager implementation ====================================================================================

    @Override
    public List<String> findAllGroups() {
        List<Group> groups = groupRepository.findAll();

        Set<String> groupNames = new HashSet<>();
        if(groups != null){
            groupNames.addAll(groups.stream().map(Group::getName).collect(Collectors.toList()));
        }

        return new ArrayList<>(groupNames);
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        Assert.hasText(groupName);

        Group group = getGroupWithName(groupName);

        Set<String> users = group.getUsers().stream().map(User::getEmail).collect(Collectors.toSet());
        return new ArrayList<>(users);
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        Assert.hasText(groupName);
        Assert.notNull(authorities);

        logger.debug("Creating new group '" + groupName + "' with authorities " +
                AuthorityUtils.authorityListToSet(authorities));

        Group group = new Group();
        group.setName(groupName);

        insertGroupAuthorities(group, authorities);

        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(String groupName) {
        List<Group> models = groupRepository.findByName(groupName);
        if(models != null){
            groupRepository.delete(models);
        }
    }

    @Override
    public void renameGroup(String oldName, String newName) {
        Group model = getGroupWithName(oldName);

        model.setName(newName);
        groupRepository.saveAndFlush(model);
    }

    @Override
    public void addUserToGroup(String username, String groupName) {
        Group group = getGroupWithName(groupName);

        User user = getUserWithName(username);
        group.addUser(user);
        groupRepository.saveAndFlush(group);
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        Group group = getGroupWithName(groupName);

        User user = getUserWithName(username);
        group.removeUser(user);
        groupRepository.saveAndFlush(group);
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        logger.debug("Loading authorities for group '" + groupName + "'");
        Assert.hasText(groupName);

        Group group = getGroupWithName(groupName);

        return group.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        Group group = getGroupWithName(groupName);

        Role role = getRoleWithAuthority(authority);
        group.addRole(role);
        groupRepository.saveAndFlush(group);
    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        Group group = getGroupWithName(groupName);

        Role role = getRoleWithAuthority(authority);
        group.removeRole(role);
        groupRepository.saveAndFlush(group);
    }

    //~ UserDetailsManager implementation ==============================================================================
    @Override
    public void createUser(final UserDetails userDetails) {
        validateUserDetails(userDetails);

        JPAUserDetails jpaUserDetails = (JPAUserDetails) userDetails;

        User user = jpaUserDetails.getUser();

        if(enableEncodePassword){
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        setDefaultInformation(user);

        userRepository.save(user);
    }

    @Override
    public void updateUser(final UserDetails userDetails) {
    	validateUserDetails(userDetails);

        JPAUserDetails jpaUserDetails = (JPAUserDetails) userDetails;

        User model = jpaUserDetails.getUser();

        userRepository.saveAndFlush(model);

    	userCache.removeUserFromCache(userDetails.getUsername());
    }

    @Override
    public void deleteUser(String username) {
    	List<User> models = userRepository.loadUsersByUserName(username);
    	if(models != null){
    		userRepository.delete(models);
    	}
    	
    	userCache.removeUserFromCache(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    	Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
                    "for current userDetails.");
        }
        String username = currentUser.getName();

     // If an authentication manager has been set, re-authenticate the userDetails with the supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating userDetails '"+ username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for userDetails '"+ username + "'");

        User model = getUserWithName(username);
        
        if(enableEncodePassword){
        	model.setPassword(passwordEncoder.encode(newPassword));
        } else {
            model.setPassword(newPassword);
        }
        
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));

        userCache.removeUserFromCache(username);
    }

	@Override
    public boolean userExists(String username) {
        List<User> models = userRepository.loadUsersByUserName(username);

        if (models.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one userDetails found with name '" + username + "'", 1);
        }

        return models.size() == 1;
    }


    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        Assert.isInstanceOf(JPAUserDetails.class, user, "UserDetails is not JPAUserDetails or the subclass");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }

    //~ RoleManager implementation ==============================================================================
    @Override
    public List<String> findAllRoles() {
        return roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> findUsersInRole(String roleName) {
        return getRoleWithName(roleName).getUsers().stream().map(User::getEmail).collect(Collectors.toList());
    }

    @Override
    public List<String> findGroupsInRole(String roleName) {
        return getRoleWithName(roleName).getGroups().stream().map(Group::getName).collect(Collectors.toList());
    }

    @Override
    public void createRole(String roleName) {
        if(!roleExists(roleName)){
            Role role = new Role();
            role.setName(roleName);
            role.setEnable(true);
            roleRepository.save(role);
        }
    }

    @Override
    public void deleteRole(String roleName) {
        Role role = getRoleWithName(roleName);

        roleRepository.delete(role);
    }

    @Override
    public void renameRole(String oldName, String newName) {
        Role role = getRoleWithName(oldName);

        if(!roleExists(newName)){
            role.setName(newName);
            roleRepository.saveAndFlush(role);
        }
    }

    @Override
    public boolean roleExists(String roleName){
        List<Role> roles = roleRepository.findByName(roleName);

        if (roles.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one userDetails found with name '" + roleName + "'", 1);
        }

        return roles.size() == 1;
    }

    @Override
    public void addUserAuthority(String username, GrantedAuthority authority) {
        User user = getUserWithName(username);
        Role role = getRoleWithAuthority(authority);
        user.addRole(role);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void removeUserAuthority(String username, GrantedAuthority authority) {
        User user = getUserWithName(username);
        Role role = getRoleWithAuthority(authority);
        user.removeRole(role);
        userRepository.saveAndFlush(user);
    }


    //~ private method for internal use
    private Group getGroupWithName(String groupName) {
        List<Group> groups = groupRepository.findByName(groupName);
        return groups.get(0);
    }


    private User getUserWithName(String username) {
        List<User> users = userRepository.loadUsersByUserName(username);

        return users.get(0);
    }

    private void insertGroupAuthorities(Group group, List<GrantedAuthority> authorities) {
        Set<Role> roles = getRolesWithAuthorities(authorities);
        group.setRoles(roles);
    }

    private Role getRoleWithAuthority(GrantedAuthority authority) {
        return getRoleWithName(authority.getAuthority());
    }

    private Role getRoleWithName(String roleName){
        List<Role> roles = roleRepository.findByName(roleName);
        return roles.get(0);
    }

    private void setDefaultInformation(User model) {
        jpaUserDetailsDefaults.initialSettings(model);
    }

    private Set<Role> getRolesWithAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<Role> roles = new HashSet<>();
        for (GrantedAuthority auth : authorities) {
            roles.addAll(roleRepository.findByName(auth.getAuthority()));
        }
        return roles;
    }

    private Authentication createNewAuthentication(Authentication currentUser,
                                                   String newPassword) {
        UserDetails user = loadUserByUsername(currentUser.getName());

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        newAuthentication.setDetails(currentUser.getDetails());

        return newAuthentication;
    }

}
