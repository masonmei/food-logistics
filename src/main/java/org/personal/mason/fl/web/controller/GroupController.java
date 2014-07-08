package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.Group;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.GroupRepository;
import org.personal.mason.fl.domain.repository.RoleRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.personal.mason.fl.utils.convert.GroupConverter;
import org.personal.mason.fl.web.pojo.PoGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
@Controller
public class GroupController extends AbstractController{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"group"}
    )
    public ResponseEntity<List<PoGroup>> findAll() {
        List<PoGroup> poGroups = GroupConverter.fromModel(groupRepository.findAll());
        if (poGroups.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(poGroups, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"group/{id}"}
    )
    public ResponseEntity<PoGroup> findById(@PathVariable Long id) {
        PoGroup poGroup = GroupConverter.fromModel(groupRepository.findOne(id));
        if (poGroup == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poGroup, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"group"}
    )
    public ResponseEntity createGroup(@RequestBody PoGroup poGroup, UriComponentsBuilder builder) {
        try {
            Group group = GroupConverter.toModel(poGroup);
            groupRepository.save(group);
            HttpHeaders headers = new HttpHeaders();

            headers.setLocation(builder.path("group/" + group.getId()).build().toUri());
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"group/{id}"}
    )
    public ResponseEntity updateRole(@PathVariable Long id, @RequestBody PoGroup poGroup, UriComponentsBuilder builder) {
        try {
            Group group = GroupConverter.toModel(poGroup);
            Group model = groupRepository.findOne(id);
            updateModel(model, group);
            groupRepository.saveAndFlush(model);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("group/" + model.getId()).build().toUri());
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"group/{id}"}
    )
    public ResponseEntity partialUpdateRole(@PathVariable Long id, @RequestBody PoGroup poGroup, UriComponentsBuilder builder) {
        try {
            Group group = GroupConverter.toModel(poGroup);
            Group model = groupRepository.findOne(id);
            mergeModel(model, group);
            groupRepository.saveAndFlush(model);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("group/" + model.getId()).build().toUri());
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"group/{id}"}
    )
    public ResponseEntity<PoGroup> delete(@PathVariable Long id) {
        try {
            groupRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"group/{gid}/user/{uid}"}
    )
    public ResponseEntity addUserToGroup(@PathVariable Long gid, @PathVariable Long uid){
        try{
            Group group = groupRepository.findOne(gid);
            User user = userRepository.findOne(uid);
            group.addUser(user);
            groupRepository.saveAndFlush(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"group/{gid}/user/{uid}"}
    )
    public ResponseEntity removeUserToGroup(@PathVariable Long gid, @PathVariable Long uid){
        try{
            Group group = groupRepository.findOne(gid);
            User user = userRepository.findOne(uid);
            group.removeUser(user);
            groupRepository.saveAndFlush(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"group/{gid}/role/{uid}"}
    )
    public ResponseEntity addRoleToGroup(@PathVariable Long gid, @PathVariable Long uid){
        try{
            Group group = groupRepository.findOne(gid);
            Role role = roleRepository.findOne(uid);
            group.addRole(role);
            groupRepository.saveAndFlush(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"group/{gid}/role/{uid}"}
    )
    public ResponseEntity removeRoleToGroup(@PathVariable Long gid, @PathVariable Long uid){
        try{
            Group group = groupRepository.findOne(gid);
            Role role = roleRepository.findOne(uid);
            group.removeRole(role);
            groupRepository.saveAndFlush(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}