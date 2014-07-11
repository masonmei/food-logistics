package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by mason on 7/3/14.
 */
@Entity
@Table(name = "fl_shopping_car")
public class ShoppingCar extends Auditing<User, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6102492541996981061L;
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

	public ShoppingCarItem addShoppingCarItem(ShoppingCarItem shoppingCarItem) {
		getShoppingCarItems().add(shoppingCarItem);
		shoppingCarItem.setShoppingCar(this);
		return shoppingCarItem;
	}

	public ShoppingCarItem removeShoppingCarItem(ShoppingCarItem shoppingCarItem) {
		getShoppingCarItems().remove(shoppingCarItem);
		shoppingCarItem.setShoppingCar(null);
		return shoppingCarItem;
	}
}
