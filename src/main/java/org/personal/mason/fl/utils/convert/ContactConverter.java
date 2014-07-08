package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Contact;
import org.personal.mason.fl.web.pojo.PoContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class ContactConverter {

    public static PoContact fromModel(Contact model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoContact fromModel(Contact model, ConvertType convertType) {
        if (model != null) {
            PoContact poContact = new PoContact();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()){
                poContact.setId(model.getId());
            }
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                poContact.setAddress(model.getAddress());
                poContact.setPhone(model.getPhone());
                poContact.setReceipt(model.getReceipt());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()){
                poContact.setPoProfile(
                        ProfileConverter.fromModel(model.getProfile(), ConvertType.KEY_ONLY)
                );
            }
            return poContact;
        }

        return null;
    }

    public static List<PoContact> fromModel(List<Contact> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoContact> fromModel(List<Contact> models, ConvertType convertType) {
        List<PoContact> poContacts = new ArrayList<>();
        if (models != null) {
            for (Contact model : models) {
                PoContact poContact = fromModel(model, convertType);

                if (poContact != null) {
                    poContacts.add(poContact);
                }
            }
        }
        return poContacts;
    }

    public static Contact toModel(PoContact poContact){
        return toModel(poContact, ConvertType.FULL);
    }

    public static Contact toModel(PoContact poContact, ConvertType convertType) {
        Contact model = null;

        if (poContact != null) {
            model = new Contact(poContact.getId());

            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setAddress(poContact.getAddress());
                model.setPhone(poContact.getPhone());
                model.setReceipt(poContact.getReceipt());
            }

            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setProfile(
                        ProfileConverter.toModel(poContact.getPoProfile(), ConvertType.KEY_ONLY)
                );
            }
        }

        return model;
    }

    public static List<Contact> toModel(List<PoContact> poContacts){
        return toModel(poContacts, ConvertType.FULL);
    }

    public static List<Contact> toModel(List<PoContact> poContacts, ConvertType convertType) {
        List<Contact> models = new ArrayList<>();
        if (poContacts != null) {
            for (PoContact poContact : poContacts) {
                Contact model = toModel(poContact, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
