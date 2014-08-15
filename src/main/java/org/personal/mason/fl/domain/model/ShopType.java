package org.personal.mason.fl.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by mason on 8/9/14.
 */
@Entity
@Table(name = "fl_shop_type")
public class ShopType extends Auditing<User, Long> implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
