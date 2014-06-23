package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.domain.model.UserRole;
import org.personal.mason.fl.web.pojo.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public class UserRoleConverter {

    public static Role fromModel(UserRole model) {
        Role role = null;

        if (model != null) {
            role = new Role();
            role.setId(model.getId());
            role.setName(model.getRoleName());
            role.setLevel(model.getRoleLevel());
            role.setType(model.getRoleType());
        }

        return role;
    }

    public static List<Role> fromModel(List<UserRole> models) {
        List<Role> roles = new ArrayList<>();
        if (models != null) {
            for (UserRole model : models) {
                Role role = fromModel(model);

                if (role != null) {
                    roles.add(role);
                }
            }
        }
        return roles;
    }

    public static UserRole toModel(Role role) {
        UserRole model = null;

        if (role != null) {
            model = new UserRole(role.getId());
            model.setRoleName(role.getName());
            model.setRoleType(role.getType());
            model.setRoleLevel(role.getLevel());
        }

        return model;
    }

    public static List<UserRole> toModel(List<Role> roles) {
        List<UserRole> models = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                UserRole model = toModel(role);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}
