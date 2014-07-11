package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the user_contact database table.
 */
@Entity
@Table(name = "fl_contact")
public class Contact extends Auditing<User, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "receipt", nullable = true)
	private String receipt;
	@Column(name = "phone", nullable = false)
	private String phone;
	@Column(name = "address", nullable = false)
	private String address;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;

	@OneToMany(mappedBy = "contact")
	private Set<Order> orders = new HashSet<>();

	public Contact() {
	}

	public Contact(Long id) {
		setId(id);
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setContact(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setContact(null);

		return order;
	}
}