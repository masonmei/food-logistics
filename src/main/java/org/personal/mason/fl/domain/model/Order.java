package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by mason on 6/28/14.
 */
@Entity
@Table(name = "fl_order")
public class Order extends Auditing<User, Long> implements Serializable {

	private static final long serialVersionUID = 6659447791463340156L;

	@Column(name = "order_number", nullable = false, unique = true)
	private String orderNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submit_time")
	private Date submitTime;

	@Column(name = "total", precision = 10, scale = 2)
	private BigDecimal total;

	@Column(name = "delivery_fee", precision = 10, scale = 2)
	private BigDecimal deliveryFee;

	@ManyToOne
	@JoinColumn(name = "cavalier_id")
	private User cavalier;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "contact_id", nullable = false)
	private Contact contact;

	@ManyToOne
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;

	@Column(name = "status", nullable = false)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "meetTime", nullable = false)
	private Date meetTime;

	@Column(name = "addition_info", nullable = false)
	private String additionInfo;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = PurchaseItem.class, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private Set<PurchaseItem> purchaseItems = new HashSet<>();

	public Order() {
	}

	public Order(Long id) {
		setId(id);
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public User getCavalier() {
		return cavalier;
	}

	public void setCavalier(User cavalier) {
		this.cavalier = cavalier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(Set<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public PurchaseItem addPurchaseItem(PurchaseItem purchaseItem) {
		getPurchaseItems().add(purchaseItem);
		purchaseItem.setOrder(this);
		return purchaseItem;
	}

	public PurchaseItem removePurchaseItem(PurchaseItem purchaseItem) {
		getPurchaseItems().remove(purchaseItem);
		purchaseItem.setOrder(null);

		return purchaseItem;
	}

	public Date getMeetTime() {
		return meetTime;
	}

	public void setMeetTime(Date meetTime) {
		this.meetTime = meetTime;
	}

	public String getAdditionInfo() {
		return additionInfo;
	}

	public void setAdditionInfo(String additionInfo) {
		this.additionInfo = additionInfo;
	}
}
