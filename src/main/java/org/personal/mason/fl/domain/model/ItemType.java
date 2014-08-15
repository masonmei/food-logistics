package org.personal.mason.fl.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by mason on 8/9/14.
 */
@Entity
@Table(name = "fl_item_type")
public class ItemType extends Auditing<User, Long> implements Serializable{
    private String name;
    private ItemType parent;
}
