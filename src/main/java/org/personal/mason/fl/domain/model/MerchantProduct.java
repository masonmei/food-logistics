package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the merchant_product database table.
 */
@Entity
@Table(name = "merchant_product")
@NamedQuery(name = "MerchantProduct.findAll", query = "SELECT m FROM MerchantProduct m")
public class MerchantProduct extends AbstractAuditable<User, Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Boolean available;

    @Column(name = "product_logo")
    private byte[] productLogo;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "product_price", nullable = false, precision = 10)
    private BigDecimal productPrice;

    @OneToMany(mappedBy = "merchantProduct")
    private Set<CommentProduct> commentProducts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    public MerchantProduct() {
    }


    public Boolean getAvailable() {
        return this.available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }


    public byte[] getProductLogo() {
        return this.productLogo;
    }

    public void setProductLogo(byte[] productLogo) {
        this.productLogo = productLogo;
    }


    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public BigDecimal getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }


    //bi-directional many-to-one association to CommentProduct
    public Set<CommentProduct> getCommentProducts() {
        return this.commentProducts;
    }

    public void setCommentProducts(Set<CommentProduct> commentProducts) {
        this.commentProducts = commentProducts;
    }

    public CommentProduct addCommentProduct(CommentProduct commentProduct) {
        getCommentProducts().add(commentProduct);
        commentProduct.setMerchantProduct(this);

        return commentProduct;
    }

    public CommentProduct removeCommentProduct(CommentProduct commentProduct) {
        getCommentProducts().remove(commentProduct);
        commentProduct.setMerchantProduct(null);

        return commentProduct;
    }


    //bi-directional many-to-one association to Merchant
    public Merchant getMerchant() {
        return this.merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}