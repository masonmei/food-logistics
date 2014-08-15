package org.personal.mason.fl.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by mason on 7/3/14.
 */

@Entity
@Table(name = "fl_shopping_car_item")
public class ShoppingCarItem extends Auditing<User, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7708521931411885053L;

	@ManyToOne
	@JoinColumn(name = "shopping_car_id", referencedColumnName = "id")
	private ShoppingCar shoppingCar;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Item item;

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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
