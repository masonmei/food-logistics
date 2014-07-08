package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 6/24/14.
 */
@JsonRootName("contact")
@JsonInclude(Include.NON_NULL)
public class PoContact {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("profile")
    private PoProfile poProfile;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("receipt")
    private String receipt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPoProfile(PoProfile poProfile) {
        this.poProfile = poProfile;
    }

    public PoProfile getPoProfile() {
        return poProfile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }


    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getReceipt() {
        return receipt;
    }
}
