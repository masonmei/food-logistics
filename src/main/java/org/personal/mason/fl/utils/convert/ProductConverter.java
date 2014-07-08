package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Product;
import org.personal.mason.fl.web.pojo.PoProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class ProductConverter {

    public static PoProduct fromModel(Product model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoProduct fromModel(Product model, ConvertType convertType) {
        PoProduct poProduct = null;

        if (model != null) {
            poProduct = new PoProduct();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()){
                poProduct.setId(model.getId());
            }
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                poProduct.setLogo(model.getLogo());
                poProduct.setName(model.getName());
                poProduct.setPrice(model.getPrice());
                poProduct.setAvailable(model.getAvailable());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()){
                poProduct.setPoMerchant(
                    MerchantConverter.fromModel(model.getMerchant(), ConvertType.KEY_ONLY)
                );
            }

        }

        return poProduct;
    }

    public static List<PoProduct> fromModel(List<Product> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoProduct> fromModel(List<Product> models, ConvertType convertType) {
        List<PoProduct> poProducts = new ArrayList<>();
        if (models != null) {
            for (Product model : models) {
                PoProduct poProduct = fromModel(model, convertType);

                if (poProduct != null) {
                    poProducts.add(poProduct);
                }
            }
        }
        return poProducts;
    }

    public static Product toModel(PoProduct poProduct){
        return toModel(poProduct, ConvertType.FULL);
    }

    public static Product toModel(PoProduct poProduct, ConvertType convertType) {
        Product model = null;

        if (poProduct != null) {
            model = new Product(poProduct.getId());

            if (convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setLogo(poProduct.getLogo());
                model.setName(poProduct.getName());
                model.setPrice(poProduct.getPrice());
                model.setAvailable(poProduct.getAvailable());
            }
            if (convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setMerchant(
                        MerchantConverter.toModel(poProduct.getPoMerchant(), ConvertType.KEY_ONLY)
                );
            }
        }
        return model;
    }

    public static List<Product> toModel(List<PoProduct> poProducts){
        return toModel(poProducts, ConvertType.FULL);
    }

    public static List<Product> toModel(List<PoProduct> poProducts, ConvertType convertType) {
        List<Product> models = new ArrayList<>();
        if (poProducts != null) {
            for (PoProduct poProduct : poProducts) {
                Product model = toModel(poProduct, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
