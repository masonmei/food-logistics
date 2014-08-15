package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the merchant database table.
 */
@Entity
@Table(name = "fl_merchant")
public class Shop extends Auditing<User, Long> implements Serializable {

	private static final long serialVersionUID = 8324229518917746781L;

	@Column(name = "location", length = 256)
	private String location;

	@Column(name = "logo")
	private String logo;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "business")
	private String business;

	@Column(name = "consume", precision = 10, scale = 2)
	private BigDecimal consume;

	@Column(name = "delivery_fee", precision = 10, scale = 2)
	private BigDecimal deliveryFee;

	@Column(name = "delivery_time")
	private Integer deliveryTime;

    @Column(name = "star_count")
    private BigDecimal starCount;
    @Column(name = "comment_count")
    private Integer commentCount;
    @Column(name = "min_consume")
    private Integer minConsume;
    @Column(name = "serve_time")
    private String serveTime;
    @Column(name = "match_num")
    private Integer matchNumber;
    @Column(name = "vip")
    private boolean vip;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "merchant")
	private Set<Comment> comments = new HashSet<>();

	@OneToMany(mappedBy = "merchant")
	private Set<Item> items = new HashSet<>();

	@OneToMany(mappedBy = "merchant")
	private Set<Order> orders = new HashSet<>();

	public Shop() {
	}

	public Shop(Long id) {
		setId(id);
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public BigDecimal getConsume() {
		return consume;
	}

	public void setConsume(BigDecimal consume) {
		this.consume = consume;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	// bi-directional many-to-one association to CommentMerchant
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Comment addCommentMerchant(Comment comment) {
		getComments().add(comment);
		comment.setShop(this);

		return comment;
	}

	public Comment removeCommentMerchant(Comment comment) {
		getComments().remove(comment);
		comment.setShop(null);

		return comment;
	}

	// bi-directional many-to-one association to MerchantProduct
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Item addMerchantProduct(Item item) {
		getItems().add(item);
		item.setShop(this);

		return item;
	}

	public Item removeMerchantProduct(Item item) {
		getItems().remove(item);
		item.setShop(null);

		return item;
	}

	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setShop(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setShop(null);

		return order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public BigDecimal getStarCount() {
        return starCount;
    }

    public void setStarCount(BigDecimal starCount) {
        this.starCount = starCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getMinConsume() {
        return minConsume;
    }

    public void setMinConsume(Integer minConsume) {
        this.minConsume = minConsume;
    }

    public String getServeTime() {
        return serveTime;
    }

    public void setServeTime(String serveTime) {
        this.serveTime = serveTime;
    }

    public Integer getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}