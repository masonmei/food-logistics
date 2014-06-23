package org.personal.mason.fl.utils.convert;

import org.personal.mason.fl.web.pojo.User;

/**
 * Created by mason on 6/22/14.
 */
public class UserConverter {
    public static User fromModel(org.personal.mason.fl.domain.model.User model) {
        if (model == null) {
            return null;
        }

        User user = new User();
        user.setId(model.getId());
        user.setDisplayName(model.getDisplayName());
        user.setEmail(model.getEmail());
        user.setPassword(model.getPassword());
        user.setPhone(model.getPhone());
        user.setUserNumber(model.getUserNumber());
        user.setVip(model.getVip());
        return user;
    }

    public static org.personal.mason.fl.domain.model.User toModel(User user) {
        if (user == null) {
            return null;
        }

        org.personal.mason.fl.domain.model.User model = new org.personal.mason.fl.domain.model.User(user.getId());

        model.setDisplayName(user.getDisplayName());
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        model.setPhone(user.getPhone());
        model.setUserNumber(user.getUserNumber());
        model.setVip(user.getVip());
        return model;
    }
}
