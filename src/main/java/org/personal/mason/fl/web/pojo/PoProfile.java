package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by mason on 6/30/14.
 */
@JsonRootName("profile")
@JsonInclude(Include.NON_NULL)
public class PoProfile {
    @JsonProperty(value = "display_name")
    private String displayName;
    @JsonProperty(value = "phone")
    private String phone;
    @JsonProperty(value = "vip")
    private Boolean vip;
    @JsonProperty(value = "contacts")
    private List<PoContact> poContacts;
    private Long id;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public List<PoContact> getPoContacts() {
        return poContacts;
    }

    public void setPoContacts(List<PoContact> poContacts) {
        this.poContacts = poContacts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
