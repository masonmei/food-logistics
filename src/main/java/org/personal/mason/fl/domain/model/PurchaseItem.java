package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by mason on 6/29/14.
 */

@Entity
@Table(name = "fl_purchase_item")
public class PurchaseItem extends AbstractAuditable<User, Long> implements Serializable {
	private static final long serialVersionUID = -921123317307021253L;

	@ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "purchase_number")
    private Integer purchaseNumber;

    public PurchaseItem() {
    }

    public PurchaseItem(Long id) {
        setId(id);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}