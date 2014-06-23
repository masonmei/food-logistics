package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the comment_merchant database table.
 */
@Entity
@Table(name = "comment_merchant")
@NamedQuery(name = "CommentMerchant.findAll", query = "SELECT c FROM CommentMerchant c")
public class CommentMerchant extends AbstractAuditable<User, Long> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2812641269452828655L;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_poster", nullable = false)
    private BigInteger commentPoster;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    public CommentMerchant() {
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

    //bi-directional many-to-one association to Merchant
    public Merchant getMerchant() {
        return this.merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}