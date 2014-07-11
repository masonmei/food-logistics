package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.common.JpaUserDetailsDefaults;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.OrderRepository;
import org.personal.mason.fl.domain.repository.RoleRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.personal.mason.fl.utils.Constrains;
import org.personal.mason.fl.utils.convert.UserConverter;
import org.personal.mason.fl.web.pojo.PoCavalier;
import org.personal.mason.fl.web.pojo.PoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 7/2/14.
 */
@Controller
public class UserController extends AbstractController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JpaUserDetailsDefaults jpaUserDetailsDefaults;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"user/search"}, method = RequestMethod.GET)
    public ResponseEntity<List<PoUser>> ueryUser(@RequestParam String query){
        List<User> userList = userRepository.findByEmailOrUserNumberOrPhone(String.format("%%%s%%", query));
        List<PoUser> users = UserConverter.fromModel(userList);
        return new ResponseEntity<List<PoUser>>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"user"}, method = RequestMethod.GET)
    public ResponseEntity<List<PoUser>> findAll(){
        List<PoUser> users = UserConverter.fromModel(userRepository.findAll());

        return new ResponseEntity<List<PoUser>>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"user/cavalier"}, method = RequestMethod.GET)
    public ResponseEntity<List<PoCavalier>> findAllCavalier(){
        List<User> models = userRepository.loadUsersByRoleName("CAVALIER");

        List<PoCavalier> cavaliers = new ArrayList<>();
        for (User model : models){
            PoCavalier cavalier = new PoCavalier();
            PoUser user = UserConverter.fromModel(model);
            cavalier.setPoUser(user);

            cavalier.setHandlingCount(orderRepository.countByCavalierAndStatus(model, Constrains.ORDER_STATUS[3]));
            cavalier.setNeedHandleCount(orderRepository.countByCavalierAndStatus(model, Constrains.ORDER_STATUS[2]));
            cavaliers.add(cavalier);
        }
        return new ResponseEntity<List<PoCavalier>>(cavaliers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"user/rolename"}, method = RequestMethod.GET)
    public ResponseEntity<List<PoUser>> findAllWithRole(@RequestParam String rolename){
        List<PoUser> users = UserConverter.fromModel(userRepository.loadUsersByRoleName(rolename));

        return new ResponseEntity<List<PoUser>>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"user/{id}"}
    )
    public ResponseEntity<PoUser> findById(@PathVariable Long id) {
        PoUser poUser = UserConverter.fromModel(userRepository.findOne(id));
        if (poUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poUser, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"user"}
    )
    public ResponseEntity<PoUser> createRole(@RequestBody PoUser poUser, UriComponentsBuilder builder) {
        try {
            User user = UserConverter.toModel(poUser);
            jpaUserDetailsDefaults.initialSettings(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("user/" + user.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"user/{id}"}
    )
    public ResponseEntity<PoUser> updateRole(@PathVariable Long id, @RequestBody PoUser poUser, UriComponentsBuilder builder) {
        User user = UserConverter.toModel(poUser);
        User model = userRepository.findOne(id);
        updateModel(model, user);
        User result = userRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("user/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"user/{id}"}
    )
    public ResponseEntity<PoUser> partialUpdateRole(@PathVariable Long id, @RequestBody PoUser poUser, UriComponentsBuilder builder) {
        User user = UserConverter.toModel(poUser);
        User model = userRepository.findOne(id);
        mergeModel(model, user);
        User result = userRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("user/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"user/{id}"}
    )
    public ResponseEntity<PoUser> delete(@PathVariable Long id) {
        try {
            userRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"user/{id}/role/{rid}"}
    )
    public ResponseEntity addRoleToUser(@PathVariable Long id, @PathVariable Long rid) {
        try {
            User user = userRepository.findOne(id);
            Role role = roleRepository.findOne(rid);
            user.addRole(role);
            userRepository.saveAndFlush(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"user/{id}/role/{rid}"}
    )
    public ResponseEntity removeRoleToUser(@PathVariable Long id, @PathVariable Long rid) {
        try {
            User user = userRepository.findOne(id);
            Role role = roleRepository.findOne(rid);
            user.removeRole(role);
            userRepository.saveAndFlush(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

}
