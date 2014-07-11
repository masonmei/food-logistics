package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Group;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.web.pojo.PoUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
public class UserConverter {

    public static PoUser fromModel(User model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoUser fromModel(User model, ConvertType convertType) {
        if (model == null) {
            return null;
        }

        PoUser poUser = new PoUser();
        poUser.setId(model.getId());
        if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
            poUser.setEmail(model.getEmail());
            poUser.setPassword(model.getPassword());
            poUser.setUserNumber(model.getUserNumber());
        }
        if(convertType.getPriority() >= ConvertType.FULL.getPriority()){
            poUser.setPoProfile(
                    ProfileConverter.fromModel(model.getProfile(), ConvertType.FLAT)
            );
            poUser.setPoGroups(
                    GroupConverter.fromModel(new ArrayList<Group>(model.getGroups()), ConvertType.FLAT)
            );
            poUser.setPoRoles(
                    RoleConverter.fromModel(new ArrayList<Role>(model.getRoles()), ConvertType.FLAT)
            );
        }

        return poUser;
    }

    public static List<PoUser> fromModel(List<User> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoUser> fromModel(List<User> models, ConvertType convertType) {
        List<PoUser> poUsers = new ArrayList<>();
        if (models != null) {
            for (User model : models) {
                PoUser poUser = fromModel(model, convertType);

                if (poUser != null) {
                    poUsers.add(poUser);
                }
            }
        }
        return poUsers;
    }

    public static User toModel(PoUser poUser){
        return toModel(poUser, ConvertType.FULL);
    }

    public static User toModel(PoUser poUser, ConvertType convertType) {
        if (poUser == null) {
            return null;
        }

        User model = new User(poUser.getId());
        if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
            model.setEmail(poUser.getEmail());
            model.setPassword(poUser.getPassword());
            model.setUserNumber(poUser.getUserNumber());
        }
        if(convertType.getPriority() >= ConvertType.FULL.getPriority()){
            model.setProfile(
                    ProfileConverter.toModel(poUser.getPoProfile(), ConvertType.FLAT)
            );
            model.setGroups(
                    new HashSet<>(GroupConverter.toModel(poUser.getPoGroups(), ConvertType.FLAT))
            );
            model.setRoles(
                    new HashSet<>(RoleConverter.toModel(poUser.getPoRoles(), ConvertType.FLAT))
            );
        }
        return model;
    }

    public static List<User> toModel(List<PoUser> poUsers){
        return toModel(poUsers, ConvertType.FULL);
    }

    public static List<User> toModel(List<PoUser> poUsers, ConvertType convertType) {
        List<User> models = new ArrayList<>();
        if (poUsers != null) {
            for (PoUser poUser : poUsers) {
                User model = toModel(poUser, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
