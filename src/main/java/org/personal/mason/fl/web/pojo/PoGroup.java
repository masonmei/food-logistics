package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by mason on 6/24/14.
 */
@JsonRootName("group")
@JsonInclude(Include.NON_NULL)
public class PoGroup {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("roles")
    private List<PoRole> poRoles;

    @JsonProperty("users")
    private List<PoUser> poUsers;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public List<PoRole> getPoRoles() {
        return poRoles;
    }

    public void setPoRoles(List<PoRole> poRoles) {
        this.poRoles = poRoles;
    }

    public List<PoUser> getPoUsers() {
        return poUsers;
    }

    public void setPoUsers(List<PoUser> poUsers) {
        this.poUsers = poUsers;
    }
}
