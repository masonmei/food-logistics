package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the merchant database table.
 */
@Entity
@Table(name = "merchant")
@NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m")
public class Merchant extends AbstractAuditable<User, Long> implements
        Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8324229518917746781L;

    @Column(name = "merchant_location", length = 256)
    private String merchantLocation;

    @Column(name = "merchant_logo")
    private byte[] merchantLogo;

    @Column(name = "merchant_name", nullable = false, length = 100)
    private String merchantName;

    @OneToMany(mappedBy = "merchant")
    private Set<CommentMerchant> commentMerchants = new HashSet<>();

    @OneToMany(mappedBy = "merchant")
    private Set<MerchantProduct> merchantProducts = new HashSet<>();

    public Merchant() {
    }

    public String getMerchantLocation() {
        return this.merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public byte[] getMerchantLogo() {
        return this.merchantLogo;
    }

    public void setMerchantLogo(byte[] merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    // bi-directional many-to-one association to CommentMerchant
    public Set<CommentMerchant> getCommentMerchants() {
        return this.commentMerchants;
    }

    public void setCommentMerchants(Set<CommentMerchant> commentMerchants) {
        this.commentMerchants = commentMerchants;
    }

    public CommentMerchant addCommentMerchant(CommentMerchant commentMerchant) {
        getCommentMerchants().add(commentMerchant);
        commentMerchant.setMerchant(this);

        return commentMerchant;
    }

    public CommentMerchant removeCommentMerchant(CommentMerchant commentMerchant) {
        getCommentMerchants().remove(commentMerchant);
        commentMerchant.setMerchant(null);

        return commentMerchant;
    }

    // bi-directional many-to-one association to MerchantProduct
    public Set<MerchantProduct> getMerchantProducts() {
        return this.merchantProducts;
    }

    public void setMerchantProducts(Set<MerchantProduct> merchantProducts) {
        this.merchantProducts = merchantProducts;
    }

    public MerchantProduct addMerchantProduct(MerchantProduct merchantProduct) {
        getMerchantProducts().add(merchantProduct);
        merchantProduct.setMerchant(this);

        return merchantProduct;
    }

    public MerchantProduct removeMerchantProduct(MerchantProduct merchantProduct) {
        getMerchantProducts().remove(merchantProduct);
        merchantProduct.setMerchant(null);

        return merchantProduct;
    }

}