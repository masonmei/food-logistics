package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

/**
 * Created by mason on 6/24/14.
 */
@JsonRootName("product")
@JsonInclude(Include.NON_NULL)
public class PoProduct {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("merchant")
    private PoMerchant poMerchant;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("available")
    private Boolean available;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setPoMerchant(PoMerchant poMerchant) {
        this.poMerchant = poMerchant;
    }

    public PoMerchant getPoMerchant() {
        return poMerchant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        if(available == null){
            available = Boolean.TRUE;
        }
        return available;
    }
}
