package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.PurchaseItem;
import org.personal.mason.fl.web.pojo.PoPurchaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class PurchaseItemConverter {

    public static PoPurchaseItem fromModel(PurchaseItem model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoPurchaseItem fromModel(PurchaseItem model, ConvertType convertType) {
        PoPurchaseItem poPurchaseItem = null;

        if (model != null) {
            poPurchaseItem = new PoPurchaseItem();
            poPurchaseItem.setId(model.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                poPurchaseItem.setNumber(model.getPurchaseNumber());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                poPurchaseItem.setPoProduct(
                    ProductConverter.fromModel(model.getProduct(), ConvertType.FLAT)
                );

                poPurchaseItem.setOrder(
                    OrderConverter.fromModel(model.getOrder(), ConvertType.KEY_ONLY)
                );
            }
        }

        return poPurchaseItem;
    }

    public static List<PoPurchaseItem> fromModel(List<PurchaseItem> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoPurchaseItem> fromModel(List<PurchaseItem> models, ConvertType convertType) {
        List<PoPurchaseItem> poPurchaseItems = new ArrayList<>();
        if (models != null) {
            for (PurchaseItem model : models) {
                PoPurchaseItem poPurchaseItem = fromModel(model, convertType);

                if (poPurchaseItem != null) {
                    poPurchaseItems.add(poPurchaseItem);
                }
            }
        }
        return poPurchaseItems;
    }


    public static PurchaseItem toModel(PoPurchaseItem poPurchaseItem){
        return toModel(poPurchaseItem, ConvertType.FULL);
    }

    public static PurchaseItem toModel(PoPurchaseItem poPurchaseItem, ConvertType convertType) {
        PurchaseItem model = null;

        if (poPurchaseItem != null) {
            model = new PurchaseItem(poPurchaseItem.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setPurchaseNumber(poPurchaseItem.getNumber());
            }

            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setOrder(
                        OrderConverter.toModel(poPurchaseItem.getOrder(), ConvertType.KEY_ONLY)
                );
                model.setProduct(
                    ProductConverter.toModel(poPurchaseItem.getPoProduct(), ConvertType.KEY_ONLY)
                );
            }
        }

        return model;
    }

    public static List<PurchaseItem> toModel(List<PoPurchaseItem> poPurchaseItems){
        return toModel(poPurchaseItems, ConvertType.FULL);
    }
    public static List<PurchaseItem> toModel(List<PoPurchaseItem> poPurchaseItems, ConvertType convertType) {
        List<PurchaseItem> models = new ArrayList<>();
        if (poPurchaseItems != null) {
            for (PoPurchaseItem poPurchaseItem : poPurchaseItems) {
                PurchaseItem model = toModel(poPurchaseItem, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
