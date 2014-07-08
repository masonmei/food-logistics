package org.personal.mason.fl.common;

import org.personal.mason.fl.domain.model.Group;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.GroupRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mason on 6/30/14.
 */
public class JPAUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(JPAUserDetailsService.class);
    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    private boolean usernameBasedPrimaryKey = true;
    private boolean enableGroups;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails>  users = loadUserDetailsByUsername(username);

        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(
                    messages.getMessage("JPAUserDetailsService.notFound", new Object[]{username}, "Username {0} not found"));
        }

        UserDetails user = users.get(0);

        List<GrantedAuthority> dbAuths = loadUserAuthorities(user);

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(
                    messages.getMessage("JPAUserDetailsService.noAuthority",
                            new Object[] {username}, "User {0} has no GrantedAuthority"));
        }

        return createUserDetails(username, user, dbAuths);
    }

    protected void addCustomAuthorities(String username, List<GrantedAuthority> authorities) {}

    protected UserDetails createUserDetails(String username, UserDetails userFromQuery,  List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromQuery.getUsername();

        if (!usernameBasedPrimaryKey) {
            returnUsername = username;
        }

        return new org.springframework.security.core.userdetails.User(returnUsername, userFromQuery.getPassword(), userFromQuery.isEnabled(),
                true, true, true, combinedAuthorities);
    }

    protected List<UserDetails> loadUserDetailsByUsername(String username){
        List<User> users =  userRepository.loadUsersByUserName(username);

        List<UserDetails> userDetailsList = new ArrayList<>();
        if(users != null){
            for(User user : users){
                JPAUserDetails jpaUserDetails = new JPAUserDetails(user);
                userDetailsList.add(jpaUserDetails);
            }
        }
        return userDetailsList;
    }

    protected List<GrantedAuthority> loadUserAuthorities(UserDetails userDetails){
        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if(userDetails instanceof JPAUserDetails){
            JPAUserDetails jpaUserDetails = (JPAUserDetails) userDetails;

            dbAuthsSet.addAll(loadUserAuthorities(jpaUserDetails.getUser()));

            if(enableGroups){
                for (Group group : jpaUserDetails.getUser().getGroups()){
                    dbAuthsSet.addAll(loadGroupAuthorities(group));
                }
            }
        }
        return new ArrayList<>(dbAuthsSet);
    }

    protected List<GrantedAuthority> loadUserAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(user != null){
            for (Role role : user.getRoles()){
                if(!role.getEnable()){
                    continue;
                }

                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }

        return authorities;
    }

    protected List<GrantedAuthority> loadGroupAuthorities(Group group){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(enableGroups && group != null && group.getEnable()){
            for (Role role : group.getRoles()){
                if(!role.getEnable()){
                    continue;
                }

                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }

        return authorities;
    }

}
