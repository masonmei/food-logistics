package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigInteger;

/**
 * Created by mason on 6/24/14.
 */
@JsonRootName("comment_merchant")
@JsonInclude(Include.NON_NULL)
public class PoComment {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("poster")
    private BigInteger poster;
    @JsonProperty("merchant")
    private PoMerchant poMerchant;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setPoster(BigInteger poster) {
        this.poster = poster;
    }

    public BigInteger getPoster() {
        return poster;
    }

    public void setPoMerchant(PoMerchant poMerchant) {
        this.poMerchant = poMerchant;
    }

    public PoMerchant getPoMerchant() {
        return poMerchant;
    }
}
