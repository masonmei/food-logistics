package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Group;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.web.pojo.PoGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class GroupConverter {

    public static PoGroup fromModel(Group model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoGroup fromModel(Group model, ConvertType convertType) {
        PoGroup poGroup = null;

        if (model != null) {
            poGroup = new PoGroup();
            poGroup.setId(model.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                poGroup.setName(model.getName());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                poGroup.setPoRoles(
                        RoleConverter.fromModel(new ArrayList<Role>(model.getRoles()), ConvertType.FLAT)
                );
            }
        }

        return poGroup;
    }

    public static List<PoGroup> fromModel(List<Group> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoGroup> fromModel(List<Group> models, ConvertType convertType) {
        List<PoGroup> poGroups = new ArrayList<>();
        if (models != null) {
            for (Group model : models) {
                PoGroup poGroup = fromModel(model, convertType);

                if (poGroup != null) {
                    poGroups.add(poGroup);
                }
            }
        }
        return poGroups;
    }


    public static Group toModel(PoGroup poGroup){
        return toModel(poGroup, ConvertType.FULL);
    }


    public static Group toModel(PoGroup poGroup, ConvertType convertType) {
        Group model = null;

        if (poGroup != null) {
            model = new Group(poGroup.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setName(poGroup.getName());
            }
            if(convertType.getPriority() >= ConvertType.FULL.getPriority()) {
                model.setRoles(
                        new HashSet<>(RoleConverter.toModel(poGroup.getPoRoles(), ConvertType.KEY_ONLY))
                );
                model.setUsers(
                        new HashSet<>(UserConverter.toModel(poGroup.getPoUsers(), ConvertType.KEY_ONLY))
                );
            }
        }

        return model;
    }

    public static List<Group> toModel(List<PoGroup> poGroups){
        return toModel(poGroups, ConvertType.FULL);
    }

    public static List<Group> toModel(List<PoGroup> poGroups, ConvertType convertType) {
        List<Group> models = new ArrayList<>();
        if (poGroups != null) {
            for (PoGroup poGroup : poGroups) {
                Group model = toModel(poGroup, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
