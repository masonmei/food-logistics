package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * The persistent class for the comment_product database table.
 */
@Entity
@Table(name = "comment_product")
@NamedQuery(name = "CommentProduct.findAll", query = "SELECT c FROM CommentProduct c")
public class CommentProduct extends AbstractAuditable<User, Long> implements
        Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1460211360185812208L;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_poster", nullable = false)
    private BigInteger commentPoster;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private MerchantProduct merchantProduct;

    public CommentProduct() {
    }

    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public BigInteger getCommentPoster() {
        return this.commentPoster;
    }

    public void setCommentPoster(BigInteger commentPoster) {
        this.commentPoster = commentPoster;
    }

    // bi-directional many-to-one association to MerchantProduct
    public MerchantProduct getMerchantProduct() {
        return this.merchantProduct;
    }

    public void setMerchantProduct(MerchantProduct merchantProduct) {
        this.merchantProduct = merchantProduct;
    }

}