package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.service.UserService;
import org.personal.mason.fl.utils.convert.UserConverter;
import org.personal.mason.fl.web.pojo.Password;
import org.personal.mason.fl.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mason on 6/22/14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"user/login"}, method = RequestMethod.POST)
    public void login(@RequestBody User user) {

    }

    @RequestMapping(value = {"user/logout"}, method = RequestMethod.GET)
    public void logout() {

    }

    @RequestMapping(value = {"user/resetpasswd"}, method = RequestMethod.POST)
    public void resetPassword(@RequestBody Password password) {

    }

    @RequestMapping(value = {"user/changepasswd"}, method = RequestMethod.POST)
    public void changePassword(@RequestBody Password password) {

    }

    @RequestMapping(value = {"user", "user/register"}, method = RequestMethod.POST)
    @ResponseBody
    public Long register(@RequestBody User user) {
        Long id = userService.add(UserConverter.toModel(user));
        return id;
    }

    @RequestMapping(value = {"user/{id}"}, method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id, @RequestBody User user) {

    }

    @RequestMapping(value = {"user/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public User viewUser(@PathVariable Long id) {
        User user = UserConverter.fromModel(userService.findById(id));
        return user;
    }

}
