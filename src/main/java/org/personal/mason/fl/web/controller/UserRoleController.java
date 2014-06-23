package org.personal.mason.fl.web.controller;

import com.sun.javafx.css.converters.URLConverter;
import org.personal.mason.fl.domain.service.UserRoleService;
import org.personal.mason.fl.utils.convert.UserRoleConverter;
import org.personal.mason.fl.web.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import sun.misc.Contended;

import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
@Controller
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    public List<Role> findAll() {
        return UserRoleConverter.fromModel(userRoleService.findAll());
    }

    public Role findById(@PathVariable Long id) {
        return UserRoleConverter.fromModel(userRoleService.findById(id));
    }

    public void delete(@PathVariable Long id) {
        userRoleService.delete(id);
    }

}
