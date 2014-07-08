package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mason on 7/3/14.
 */

@Entity
@Table(name="fl_shopping_car_item")
public class ShoppingCarItem extends AbstractAuditable<User, Long> implements Serializable {

    @ManyToOne
    @JoinColumn(name = "shopping_car_id", referencedColumnName = "id")
    private ShoppingCar shoppingCar;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "number")
    private Integer number;

    public ShoppingCarItem() {
    }

    public ShoppingCarItem(Long id) {
        setId(id);
    }

    public ShoppingCar getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(ShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}