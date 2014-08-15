package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the comment_merchant database table.
 */
@Entity
@Table(name = "fl_comment")
public class Comment extends Auditing<User, Long> implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 2812641269452828655L;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "poster", nullable = false)
	private BigInteger poster;

	@ManyToOne
	@JoinColumn(name = "merchant_id", nullable = false)
	private Shop shop;

	public Comment() {
	}

	public Comment(Long id) {
		setId(id);
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigInteger getPoster() {
		return this.poster;
	}

	public void setPoster(BigInteger poster) {
		this.poster = poster;
	}

	// bi-directional many-to-one association to Merchant
	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}