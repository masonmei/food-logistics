package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.ShoppingCar;
import org.personal.mason.fl.web.pojo.PoShoppingCar;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by mason on 6/28/14.
 */
public class ShoppingCarConverter {

    public static PoShoppingCar fromModel(ShoppingCar model) {
        return fromModel(model, ConvertType.FULL);
    }

    public static PoShoppingCar fromModel(ShoppingCar model, ConvertType convertType) {
        PoShoppingCar poShoppingCar = null;

        if (model != null) {
            poShoppingCar = new PoShoppingCar();
            if (convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()) {
                poShoppingCar.setId(model.getId());
            }
            if (convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                poShoppingCar.setPoShoppingCarItems(
                        ShoppingCarItemConverter.fromModel(new ArrayList(model.getShoppingCarItems()), ConvertType.FULL)
                );
            }
        }

        return poShoppingCar;
    }

    public static ShoppingCar toModel(PoShoppingCar poShoppingCar) {
        return toModel(poShoppingCar, ConvertType.FULL);
    }

    public static ShoppingCar toModel(PoShoppingCar poShoppingCar, ConvertType convertType) {
        ShoppingCar model = null;

        if (poShoppingCar != null) {
            model = new ShoppingCar(poShoppingCar.getId());

            if (convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setShoppingCarItems(
                        new HashSet<>(ShoppingCarItemConverter.toModel(poShoppingCar.getPoShoppingCarItems(), ConvertType.FULL))
                );
            }
        }

        return model;
    }
}
