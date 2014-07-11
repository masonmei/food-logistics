package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Order;
import org.personal.mason.fl.domain.model.PurchaseItem;
import org.personal.mason.fl.web.pojo.PoOrder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mason on 6/28/14.
 */
public class OrderConverter {

    public static PoOrder fromModel(Order model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoOrder fromModel(Order model, ConvertType convertType) {
        PoOrder poOrder = null;

        if (model != null) {
            poOrder = new PoOrder();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()) {
                poOrder.setId(model.getId());
            }
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                poOrder.setTotalPrice(model.getTotal());
                poOrder.setSubmitTime(model.getSubmitTime());
                poOrder.setOrderNumber(model.getOrderNumber());
                poOrder.setDeliveryFee(model.getDeliveryFee());
                poOrder.setOrderStatus(model.getStatus());
                poOrder.setAdditionInfo(model.getAdditionInfo());
                poOrder.setMeetTime(model.getMeetTime());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                poOrder.setPoCavalier(
                        UserConverter.fromModel(model.getCavalier(), ConvertType.FLAT)
                );
                poOrder.setPoUser(
                    UserConverter.fromModel(model.getUser(), ConvertType.KEY_ONLY)
                );
                poOrder.setPoContact(
                        ContactConverter.fromModel(model.getContact(), ConvertType.FLAT)
                );
                poOrder.setPoMerchant(
                        MerchantConverter.fromModel(model.getMerchant(), ConvertType.FLAT)
                );

                poOrder.setPoPurchaseItems(
                        PurchaseItemConverter.fromModel(new ArrayList<PurchaseItem>(model.getPurchaseItems()), ConvertType.FULL)
                );
            }
        }

        return poOrder;
    }

    public static List<PoOrder> fromModel(List<Order> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoOrder> fromModel(List<Order> models, ConvertType convertType) {
        List<PoOrder> poOrders = new ArrayList<>();
        if (models != null) {
            for (Order model : models) {
                PoOrder poOrder = fromModel(model, convertType);

                if (poOrder != null) {
                    poOrders.add(poOrder);
                }
            }
        }
        return poOrders;
    }

    public static Order toModel(PoOrder poOrder){
        return toModel(poOrder, ConvertType.FULL);
    }

    public static Order toModel(PoOrder poOrder, ConvertType convertType) {
        Order model = null;

        if (poOrder != null) {
            model = new Order(poOrder.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setOrderNumber(poOrder.getOrderNumber());
                model.setDeliveryFee(poOrder.getDeliveryFee());
                model.setTotal(poOrder.getTotalPrice());
                model.setSubmitTime(poOrder.getSubmitTime());
                model.setStatus(poOrder.getOrderStatus());
                model.setAdditionInfo(poOrder.getAdditionInfo());
                model.setMeetTime(poOrder.getMeetTime());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setCavalier(
                        UserConverter.toModel(poOrder.getPoCavalier())
                );
                model.setUser(
                    UserConverter.toModel(poOrder.getPoUser(), ConvertType.KEY_ONLY)
                );
                model.setContact(
                    ContactConverter.toModel(poOrder.getPoContact(), ConvertType.KEY_ONLY)
                );
                model.setMerchant(
                    MerchantConverter.toModel(poOrder.getPoMerchant(), ConvertType.KEY_ONLY)
                );

                model.setPurchaseItems(
                    new HashSet<>(PurchaseItemConverter.toModel(poOrder.getPoPurchaseItems(), ConvertType.FULL))
                );
            }
        }

        return model;
    }

    public static List<Order> toModel(List<PoOrder> poOrders){
        return toModel(poOrders, ConvertType.FULL);
    }

    public static List<Order> toModel(List<PoOrder> poOrders, ConvertType convertType) {
        List<Order> models = new ArrayList<>();
        if (poOrders != null) {
            for (PoOrder poOrder : poOrders) {
                Order model = toModel(poOrder, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
