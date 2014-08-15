package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.ShoppingCarItem;
import org.personal.mason.fl.web.pojo.PoShoppingCarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class ShoppingCarItemConverter {

    public static PoShoppingCarItem fromModel(ShoppingCarItem model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoShoppingCarItem fromModel(ShoppingCarItem model, ConvertType convertType) {
        PoShoppingCarItem poShoppingCarItem = null;

        if (model != null) {
            poShoppingCarItem = new PoShoppingCarItem();
            poShoppingCarItem.setId(model.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                poShoppingCarItem.setNumber(model.getNumber());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                poShoppingCarItem.setPoProduct(
                    ProductConverter.fromModel(model.getItem(), ConvertType.FLAT)
                );
                poShoppingCarItem.setShoppingCar(
                    ShoppingCarConverter.fromModel(model.getShoppingCar(), ConvertType.KEY_ONLY)
                );
            }
        }

        return poShoppingCarItem;
    }

    public static List<PoShoppingCarItem> fromModel(List<ShoppingCarItem> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoShoppingCarItem> fromModel(List<ShoppingCarItem> models, ConvertType convertType) {
        List<PoShoppingCarItem> poShoppingCarItems = new ArrayList<>();
        if (models != null) {
            for (ShoppingCarItem model : models) {
                PoShoppingCarItem poShoppingCarItem = fromModel(model, convertType);

                if (poShoppingCarItem != null) {
                    poShoppingCarItems.add(poShoppingCarItem);
                }
            }
        }
        return poShoppingCarItems;
    }


    public static ShoppingCarItem toModel(PoShoppingCarItem poShoppingCarItem){
        return toModel(poShoppingCarItem, ConvertType.FULL);
    }

    public static ShoppingCarItem toModel(PoShoppingCarItem poShoppingCarItem, ConvertType convertType) {
        ShoppingCarItem model = null;

        if (poShoppingCarItem != null) {
            model = new ShoppingCarItem(poShoppingCarItem.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setNumber(poShoppingCarItem.getNumber());
            }

            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setShoppingCar(
                        ShoppingCarConverter.toModel(poShoppingCarItem.getShoppingCar(), ConvertType.KEY_ONLY)
                );
                model.setItem(
                        ProductConverter.toModel(poShoppingCarItem.getPoProduct(), ConvertType.KEY_ONLY)
                );
            }
        }

        return model;
    }

    public static List<ShoppingCarItem> toModel(List<PoShoppingCarItem> poShoppingCarItems){
        return toModel(poShoppingCarItems, ConvertType.FULL);
    }
    public static List<ShoppingCarItem> toModel(List<PoShoppingCarItem> poShoppingCarItems, ConvertType convertType) {
        List<ShoppingCarItem> models = new ArrayList<>();
        if (poShoppingCarItems != null) {
            for (PoShoppingCarItem poShoppingCarItem : poShoppingCarItems) {
                ShoppingCarItem model = toModel(poShoppingCarItem, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
