package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the user_role database table.
 */
@Entity
@Table(name = "user_role")
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
public class UserRole extends AbstractAuditable<User, Long> implements
        Serializable {

    public enum RoleType {
        ROLE_USER("U"),
        ROLE_CAVALIER("C"),
        ROLE_MERCHANT("M"),
        ROLE_ADMINISTRATOR("A");

        private String key;

        private RoleType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public static RoleType get(String key) {
            for (RoleType type : RoleType.values()) {
                if (type.getKey().equals(key)) {
                    return type;
                }
            }
            return ROLE_USER;
        }
    }

    /**
     *
     */
    private static final long serialVersionUID = 2717028700261627699L;

    @Column(name = "role_level", nullable = false, length = 8)
    private String roleLevel;

    @Column(name = "role_name", nullable = false, length = 64)
    private String roleName;

    @Column(name = "role_type", nullable = false, length = 1)
    private String roleType;

    @OneToMany(mappedBy = "userRole")
    private Set<User> users = new HashSet<User>();

    @OneToMany(mappedBy = "userRole")
    private Set<UserGroup> userGroups = new HashSet<>();

    public UserRole() {
    }

    public UserRole(Long id) {
        setId(id);
    }

    public String getRoleLevel() {
        return this.roleLevel;
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    // bi-directional many-to-one association to User
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User addUser(User user) {
        getUsers().add(user);
        user.setUserRole(this);

        return user;
    }

    public User removeUser(User user) {
        getUsers().remove(user);
        user.setUserRole(null);

        return user;
    }

    // bi-directional many-to-one association to UserGroup
    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public UserGroup addUserGroup(UserGroup userGroup) {
        getUserGroups().add(userGroup);
        userGroup.setUserRole(this);

        return userGroup;
    }

    public UserGroup removeUserGroup(UserGroup userGroup) {
        getUserGroups().remove(userGroup);
        userGroup.setUserRole(null);

        return userGroup;
    }

}