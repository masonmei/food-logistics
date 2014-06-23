package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the user_register database table.
 */
@Entity
@Table(name = "user_register")
@NamedQuery(name = "UserRegister.findAll", query = "SELECT u FROM UserRegister u")
public class UserRegister extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserRegister() {
    }

}