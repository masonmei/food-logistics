package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "fl_user")
public class User extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(name = "user_number", nullable = false, length = 21)
    private String userNumber;

    @Column(name="enable")
    private Boolean enable = Boolean.TRUE;

    @ManyToMany()
    @JoinTable(name = "fl_user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="shopping_car_id")
    private ShoppingCar shoppingCar;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "cavalier")
    private Set<Order> cavalierOrder = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<PersistentToken> persistentTokens = new HashSet<>();

    public User() {
    }

    public User(Long id) {
        setId(id);
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

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Role addRole(Role role){
        if(role == null){
            return null;
        }
        getRoles().add(role);
        role.getUsers().add(this);
        return role;
    }

    public Role removeRole(Role role){
        if(role == null){
            return null;
        }
        getRoles().remove(role);
        role.getUsers().remove(this);

        return role;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Group addGroup(Group group){
        if(group == null){
            return null;
        }
        getGroups().add(group);
        group.getUsers().add(this);

        return group;
    }

    public Group removeGroup(Group group){
        if(group == null){
            return null;
        }

        getGroups().remove(group);
        group.getUsers().remove(this);

        return group;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ShoppingCar getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Order> getCavalierOrder() {
        return cavalierOrder;
    }

    public void setCavalierOrder(Set<Order> cavalierOrder) {
        this.cavalierOrder = cavalierOrder;
    }

    public Order addCavalierOrder(Order order){
        if(order == null){
            return null;
        }
        getCavalierOrder().add(order);
        order.setCavalier(this);
        return order;
    }

    public Order removeCavalierOrder(Order order){
        if(order == null){
            return null;
        }

        getCavalierOrder().remove(order);
        order.setCavalier(null);
        return order;
    }

    public Set<PersistentToken> getPersistentTokens() {
        return persistentTokens;
    }

    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
        this.persistentTokens = persistentTokens;
    }
}