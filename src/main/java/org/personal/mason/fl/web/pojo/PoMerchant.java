package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

/**
 * Created by mason on 6/24/14.
 */
@JsonRootName("merchant")
@JsonInclude(Include.NON_NULL)
public class PoMerchant {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("location")
    private String location;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("name")
    private String name;
    @JsonProperty("business")
    private String business;
    @JsonProperty("consume")
    private BigDecimal consume;
    @JsonProperty("delivery_fee")
    private BigDecimal deliveryFee;
    @JsonProperty("delivery_time")
    private Integer deliveryTime;
    @JsonProperty("user")
    private PoUser poUser;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBusiness() {
        return business;
    }

    public void setConsume(BigDecimal consume) {
        this.consume = consume;
    }

    public BigDecimal getConsume() {
        return consume;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public PoUser getPoUser() {
        return poUser;
    }

    public void setPoUser(PoUser poUser) {
        this.poUser = poUser;
    }
}
