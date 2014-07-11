package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.personal.mason.fl.utils.Constrains;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by mason on 6/28/14.
 */

@JsonRootName("order")
@JsonInclude(Include.NON_NULL)
public class PoOrder {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("user")
    private PoUser poUser;
    @JsonProperty("contact")
    private PoContact poContact;
    @JsonProperty("order_number")
    private String orderNumber;
    @JsonProperty("delivery_fee")
    private BigDecimal deliveryFee;
    @JsonProperty("merchant")
    private PoMerchant poMerchant;
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("submit_time")
    private Date submitTime;
    @JsonProperty("order_status")
    private String orderStatus;
    @JsonProperty("meet_time")
    private Date meetTime;
    @JsonProperty("addition_info")
    private String additionInfo;

    @JsonProperty("purchase_items")
    private List<PoPurchaseItem> poPurchaseItems;
    private PoUser poCavalier;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPoUser(PoUser poUser) {
        this.poUser = poUser;
    }

    public PoUser getPoUser() {
        return poUser;
    }

    public void setPoContact(PoContact poContact) {
        this.poContact = poContact;
    }

    public PoContact getPoContact() {
        return poContact;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }


    public void setPoMerchant(PoMerchant poMerchant) {
        this.poMerchant = poMerchant;
    }

    public PoMerchant getPoMerchant() {
        return poMerchant;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        if(orderStatus == null){
            orderStatus = Constrains.ORDER_STATUS[0];
        }
        return orderStatus;
    }

    public void setPoPurchaseItems(List<PoPurchaseItem> poPurchaseItems) {
        this.poPurchaseItems = poPurchaseItems;
    }

    public List<PoPurchaseItem> getPoPurchaseItems() {
        return poPurchaseItems;
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(Date meetTime) {
        this.meetTime = meetTime;
    }

    public String getAdditionInfo() {
        return additionInfo;
    }

    public void setAdditionInfo(String additionInfo) {
        this.additionInfo = additionInfo;
    }

    public PoUser getPoCavalier() {
        return poCavalier;
    }

    public void setPoCavalier(PoUser poCavalier) {
        this.poCavalier = poCavalier;
    }
}
