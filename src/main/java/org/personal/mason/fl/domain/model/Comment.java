package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;


/**
 * The persistent class for the comment_merchant database table.
 */
@Entity
@Table(name = "fl_comment")
public class Comment extends AbstractAuditable<User, Long> implements Serializable {

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
    private Merchant merchant;

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

    //bi-directional many-to-one association to Merchant
    public Merchant getMerchant() {
        return this.merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}