package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the user_forget database table.
 */
@Entity
@Table(name = "user_forget")
@NamedQuery(name = "UserForget.findAll", query = "SELECT u FROM UserForget u")
public class UserForget extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserForget() {
    }

}