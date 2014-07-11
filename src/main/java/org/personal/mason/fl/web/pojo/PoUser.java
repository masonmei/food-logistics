package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
@JsonRootName("user")
@JsonInclude(Include.NON_NULL)
public class PoUser {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("user_number")
    private String userNumber;
    @JsonProperty("profile")
    private PoProfile poProfile;

    @JsonProperty("roles")
    private List<PoRole> poRoles;
    @JsonProperty("groups")
    private List<PoGroup> poGroups;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public PoProfile getPoProfile() {
        return poProfile;
    }

    public void setPoProfile(PoProfile poProfile) {
        this.poProfile = poProfile;
    }

    public List<PoRole> getPoRoles() {
        return poRoles;
    }

    public void setPoRoles(List<PoRole> poRoles) {
        this.poRoles = poRoles;
    }

    public List<PoGroup> getPoGroups() {
        return poGroups;
    }

    public void setPoGroups(List<PoGroup> poGroups) {
        this.poGroups = poGroups;
    }
}
