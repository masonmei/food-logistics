package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the user_contact database table.
 */
@Entity
@Table(name = "user_contact")
@NamedQuery(name = "UserContact.findAll", query = "SELECT u FROM UserContact u")
public class UserContact extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserContact() {
    }

    //bi-directional many-to-one association to User
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}