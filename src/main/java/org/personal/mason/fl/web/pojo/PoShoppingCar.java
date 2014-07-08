package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by mason on 7/3/14.
 */

@JsonRootName("shopping_car")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PoShoppingCar {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("items")
    private List<PoShoppingCarItem> poShoppingCarItems;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPoShoppingCarItems(List<PoShoppingCarItem> poShoppingCarItems) {
        this.poShoppingCarItems = poShoppingCarItems;
    }

    public List<PoShoppingCarItem> getPoShoppingCarItems() {
        return poShoppingCarItems;
    }
}
