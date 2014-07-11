package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 7/7/14.
 */
@JsonRootName("checkout")
public class PoCheckout {

    @JsonProperty("contact")
    private PoContact poContact;
    @JsonProperty("addition_info")
    private String additionInfo;
    @JsonProperty("meet_time")
    private String meetTime;

    public PoContact getPoContact() {
        return poContact;
    }

    public void setPoContact(PoContact poContact) {
        this.poContact = poContact;
    }

    public String getAdditionInfo() {
        return additionInfo;
    }

    public void setAdditionInfo(String additionInfo) {
        this.additionInfo = additionInfo;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }
}
