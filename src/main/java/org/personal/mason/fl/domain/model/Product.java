package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the merchant_product database table.
 */
@Entity
@Table(name = "fl_product")
public class Product extends Auditing<User, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Boolean available = Boolean.TRUE;

	@Column(name = "logo")
	private String logo;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;

	@OneToMany(mappedBy = "product")
	private Set<ShoppingCarItem> orders = new HashSet<>();

	public Product() {
	}

	public Product(Long id) {
		setId(id);
	}

	public Boolean getAvailable() {
		return this.available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	// bi-directional many-to-one association to Merchant
	public Merchant getMerchant() {
		return this.merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

}