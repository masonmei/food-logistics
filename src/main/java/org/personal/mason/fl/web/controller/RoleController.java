package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.repository.RoleRepository;
import org.personal.mason.fl.utils.convert.RoleConverter;
import org.personal.mason.fl.web.pojo.PoRole;
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
public class RoleController extends AbstractController {

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("permitAll")
    @RequestMapping(method = RequestMethod.GET,
            value = {"role"}
    )
    public ResponseEntity<List<PoRole>> findAll() {
        List<PoRole> poRoles = RoleConverter.fromModel(roleRepository.findAll());
        if (poRoles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poRoles, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"role"}
    )
    public ResponseEntity<PoRole> createRole(@RequestBody PoRole poRole, UriComponentsBuilder builder) {

        PoRole result = RoleConverter.fromModel(roleRepository.save(RoleConverter.toModel(poRole)));


        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("role/" + result.getId()).build().toUri());
            return new ResponseEntity<PoRole>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<PoRole>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"role/{id}"}
    )
    public ResponseEntity<PoRole> updateRole(@PathVariable Long id, @RequestBody PoRole poRole, UriComponentsBuilder builder) {
        Role role = RoleConverter.toModel(poRole);
        Role model = roleRepository.findOne(id);
        updateModel(model, role);
        Role result = roleRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("role/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"role/{id}"}
    )
    public ResponseEntity<PoRole> partialUpdateRole(@PathVariable Long id, @RequestBody PoRole poRole, UriComponentsBuilder builder) {
        Role role = RoleConverter.toModel(poRole);
        Role model = roleRepository.findOne(id);
        mergeModel(model, role);
        Role result = roleRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("role/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"role/{id}"}
    )
    public ResponseEntity<PoRole> delete(@PathVariable Long id) {
        try {
            roleRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}