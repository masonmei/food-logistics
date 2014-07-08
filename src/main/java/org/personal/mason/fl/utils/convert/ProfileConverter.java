package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Profile;
import org.personal.mason.fl.web.pojo.PoProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mason on 6/30/14.
 */
public class ProfileConverter {
    public static PoProfile fromModel(Profile model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoProfile fromModel(Profile model, ConvertType convertType) {
        PoProfile poProfile = null;

        if (model != null) {
            poProfile = new PoProfile();
            if(convertType.getPriority() >= ConvertType.KEY_ONLY.getPriority()){
                poProfile.setId(model.getId());
            }
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()){
                poProfile.setDisplayName(model.getDisplayName());
                poProfile.setPhone(model.getPhone());
                poProfile.setVip(model.getVip());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()){
                poProfile.setPoContacts(
                        ContactConverter.fromModel(new ArrayList(model.getContacts()), ConvertType.KEY_ONLY)
                );
            }

        }

        return poProfile;
    }

    public static List<PoProfile> fromModel(List<Profile> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoProfile> fromModel(List<Profile> models, ConvertType convertType) {
        List<PoProfile> poProfiles = new ArrayList<>();
        if (models != null) {
            for (Profile model : models) {
                PoProfile poProfile = fromModel(model, convertType);

                if (poProfile != null) {
                    poProfiles.add(poProfile);
                }
            }
        }
        return poProfiles;
    }

    public static Profile toModel(PoProfile poProfile){
        return toModel(poProfile, ConvertType.FULL);
    }

    public static Profile toModel(PoProfile poProfile, ConvertType convertType) {
        Profile model = null;

        if (poProfile != null) {
            model = new Profile(poProfile.getId());

            if (convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setDisplayName(poProfile.getDisplayName());
                model.setPhone(poProfile.getPhone());
                model.setVip(poProfile.getVip());
            }
            if (convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setContacts(
                        new HashSet(ContactConverter.toModel(poProfile.getPoContacts(), ConvertType.KEY_ONLY))
                );
            }
        }
        return model;
    }

    public static List<Profile> toModel(List<PoProfile> poProfiles){
        return toModel(poProfiles, ConvertType.FULL);
    }

    public static List<Profile> toModel(List<PoProfile> poProfiles, ConvertType convertType) {
        List<Profile> models = new ArrayList<>();
        if (poProfiles != null) {
            for (PoProfile poProfile : poProfiles) {
                Profile model = toModel(poProfile, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
