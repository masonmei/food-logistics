package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 7/8/14.
 */
@JsonRootName("cavalier")
public class PoCavalier {
    @JsonProperty("need_handle_count")
    private long needHandleCount;
    @JsonProperty("handling_count")
    private long handlingCount;
    @JsonProperty("user")
    private PoUser poUser;

    public long getNeedHandleCount() {
        return needHandleCount;
    }

    public void setNeedHandleCount(long needHandleCount) {
        this.needHandleCount = needHandleCount;
    }

    public long getHandlingCount() {
        return handlingCount;
    }

    public void setHandlingCount(long handlingCount) {
        this.handlingCount = handlingCount;
    }

    public PoUser getPoUser() {
        return poUser;
    }

    public void setPoUser(PoUser poUser) {
        this.poUser = poUser;
    }
}
