package org.personal.mason.fl.web.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by mason on 7/3/14.
 */
@JsonRootName("shopping_car_item")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PoShoppingCarItem {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("product")
    private PoProduct poProduct;
    @JsonProperty("shopping_car")
    private PoShoppingCar shoppingCar;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setPoProduct(PoProduct poProduct) {
        this.poProduct = poProduct;
    }

    public PoProduct getPoProduct() {
        return poProduct;
    }

    public void setShoppingCar(PoShoppingCar shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public PoShoppingCar getShoppingCar() {
        return shoppingCar;
    }
}
