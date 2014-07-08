package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mason on 7/3/14.
 */
@Entity
@Table(name="fl_shopping_car")
public class ShoppingCar extends AbstractAuditable<User, Long> implements Serializable{

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ShoppingCarItem.class, orphanRemoval = true)
    @JoinColumn(name = "shopping_car_id")
    private Set<ShoppingCarItem> shoppingCarItems = new HashSet<>();

    public ShoppingCar() {
    }

    public ShoppingCar(Long id) {
        setId(id);
    }

    public Set<ShoppingCarItem> getShoppingCarItems() {
        return shoppingCarItems;
    }

    public void setShoppingCarItems(Set<ShoppingCarItem> shoppingCarItems) {
        this.shoppingCarItems = shoppingCarItems;
    }

    public ShoppingCarItem addShoppingCarItem(ShoppingCarItem shoppingCarItem){
        getShoppingCarItems().add(shoppingCarItem);
        shoppingCarItem.setShoppingCar(this);
        return shoppingCarItem;
    }
    public ShoppingCarItem removeShoppingCarItem(ShoppingCarItem shoppingCarItem){
        getShoppingCarItems().remove(shoppingCarItem);
        shoppingCarItem.setShoppingCar(null);
        return shoppingCarItem;
    }
}
