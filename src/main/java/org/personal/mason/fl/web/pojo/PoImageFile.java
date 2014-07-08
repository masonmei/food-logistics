package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 6/25/14.
 */
@JsonRootName("image")
@JsonInclude(Include.NON_NULL)
public class PoImageFile {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private byte[] content;
    @JsonProperty("name")
    private String name;
    @JsonProperty("size")
    private Long size;
    @JsonProperty("type")
    private String type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
