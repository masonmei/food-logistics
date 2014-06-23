package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "display_name", length = 48)
    private String displayName;

    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(length = 30)
    private String phone;

    @Column(name = "user_number", nullable = false, length = 10)
    private String userNumber;

    @Column(name = "vip")
    private Boolean vip;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private Set<UserContact> userContacts = new HashSet<>();

    public User() {
    }

    public User(Long id) {
        setId(id);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Boolean getVip() {
        return this.vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }


    //bi-directional many-to-one association to UserGroup
    public UserGroup getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }


    //bi-directional many-to-one association to UserRole
    public UserRole getUserRole() {
        return this.userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    //bi-directional many-to-one association to UserContact
    public Set<UserContact> getUserContacts() {
        return this.userContacts;
    }

    public void setUserContacts(Set<UserContact> userContacts) {
        this.userContacts = userContacts;
    }

    public UserContact addUserContact(UserContact userContact) {
        getUserContacts().add(userContact);
        userContact.setUser(this);

        return userContact;
    }

    public UserContact removeUserContact(UserContact userContact) {
        getUserContacts().remove(userContact);
        userContact.setUser(null);

        return userContact;
    }

}