package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the user_group database table.
 */
@Entity
@Table(name = "user_group")
@NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u")
public class UserGroup extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "group_name", nullable = false, length = 100)
    private String groupName;

    //bi-directional many-to-one association to User
    @OneToMany(mappedBy = "userGroup")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "group_role_id", nullable = false)
    private UserRole userRole;

    public UserGroup() {
    }

    public String getGroupDescription() {
        return this.groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User addUser(User user) {
        getUsers().add(user);
        user.setUserGroup(this);

        return user;
    }

    public User removeUser(User user) {
        getUsers().remove(user);
        user.setUserGroup(null);

        return user;
    }

    //bi-directional many-to-one association to UserRole
    public UserRole getUserRole() {
        return this.userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

}