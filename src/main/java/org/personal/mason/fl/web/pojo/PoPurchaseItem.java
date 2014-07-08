package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 6/29/14.
 */
@JsonRootName("purchase_item")
@JsonInclude(Include.NON_NULL)
public class PoPurchaseItem {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("product")
    private PoProduct poProduct;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("order")
    private PoOrder order;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPoProduct(PoProduct poProduct) {
        this.poProduct = poProduct;
    }

    public PoProduct getPoProduct() {
        return poProduct;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setOrder(PoOrder order) {
        this.order = order;
    }

    public PoOrder getOrder() {
        return order;
    }
}
