package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Merchant;
import org.personal.mason.fl.web.pojo.PoMerchant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class MerchantConverter {

    public static PoMerchant fromModel(Merchant model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoMerchant fromModel(Merchant model, ConvertType convertType) {
        PoMerchant poMerchant = null;

        if (model != null) {
            poMerchant = new PoMerchant();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()){
                poMerchant.setId(model.getId());
            }
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                poMerchant.setLocation(model.getLocation());
                poMerchant.setLogo(model.getLogo());
                poMerchant.setName(model.getName());
                poMerchant.setBusiness(model.getBusiness());
                poMerchant.setConsume(model.getConsume());
                poMerchant.setDeliveryFee(model.getDeliveryFee());
                poMerchant.setDeliveryTime(model.getDeliveryTime());
            }
        }

        return poMerchant;
    }

    public static List<PoMerchant> fromModel(List<Merchant> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoMerchant> fromModel(List<Merchant> models, ConvertType convertType) {
        List<PoMerchant> poMerchants = new ArrayList<>();
        if (models != null) {
            for (Merchant model : models) {
                PoMerchant poMerchant = fromModel(model, convertType);

                if (poMerchant != null) {
                    poMerchants.add(poMerchant);
                }
            }
        }
        return poMerchants;
    }

    public static Merchant toModel(PoMerchant poMerchant){
        return toModel(poMerchant, ConvertType.FULL);
    }

    public static Merchant toModel(PoMerchant poMerchant, ConvertType convertType) {
        Merchant model = null;

        if (poMerchant != null) {
            model = new Merchant(poMerchant.getId());

            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                model.setLocation(poMerchant.getLocation());
                model.setLogo(poMerchant.getLogo());
                model.setName(poMerchant.getName());
                model.setBusiness(poMerchant.getBusiness());
                model.setConsume(poMerchant.getConsume());
                model.setDeliveryFee(poMerchant.getDeliveryFee());
                model.setDeliveryTime(poMerchant.getDeliveryTime());
            }
        }

        return model;
    }

    public static List<Merchant> toModel(List<PoMerchant> poMerchants){
        return toModel(poMerchants, ConvertType.FULL);
    }

    public static List<Merchant> toModel(List<PoMerchant> poMerchants, ConvertType convertType) {
        List<Merchant> models = new ArrayList<>();
        if (poMerchants != null) {
            for (PoMerchant poMerchant : poMerchants) {
                Merchant model = toModel(poMerchant, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
