package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.web.pojo.PoRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class RoleConverter {

    public static PoRole fromModel(Role model){
        return fromModel(model, ConvertType.FULL);
    }

    public static PoRole fromModel(Role model, ConvertType convertType) {
        PoRole poRole = null;

        if (model != null) {
            poRole = new PoRole();
            poRole.setId(model.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                poRole.setName(model.getName());
                poRole.setEnable(model.getEnable());
            }
        }

        return poRole;
    }

    public static List<PoRole> fromModel(List<Role> models){
        return fromModel(models, ConvertType.FULL);
    }

    public static List<PoRole> fromModel(List<Role> models, ConvertType convertType) {
        List<PoRole> poRoles = new ArrayList<>();
        if (models != null) {
            for (Role model : models) {
                PoRole poRole = fromModel(model, convertType);

                if (poRole != null) {
                    poRoles.add(poRole);
                }
            }
        }
        return poRoles;
    }

    public static Role toModel(PoRole poRole){
        return toModel(poRole, ConvertType.FULL);
    }

    public static Role toModel(PoRole poRole, ConvertType convertType) {
        Role model = null;

        if (poRole != null) {
            model = new Role(poRole.getId());
            if(convertType.getPriority() >= ConvertType.FLAT.getPriority()) {
                model.setName(poRole.getName());
                model.setEnable(poRole.getEnable());
            }
        }

        return model;
    }

    public static List<Role> toModel(List<PoRole> poRoles){
        return toModel(poRoles, ConvertType.FULL);
    }

    public static List<Role> toModel(List<PoRole> poRoles, ConvertType convertType) {
        List<Role> models = new ArrayList<>();
        if (poRoles != null) {
            for (PoRole poRole : poRoles) {
                Role model = toModel(poRole, convertType);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
