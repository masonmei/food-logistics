package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 6/22/14.
 */
@JsonRootName("user")
public class User {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "display_name")
    private String displayName;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "phone")
    private String phone;
    @JsonProperty(value = "user_number")
    private String userNumber;
    @JsonProperty(value = "vip")
    private Boolean vip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
}
