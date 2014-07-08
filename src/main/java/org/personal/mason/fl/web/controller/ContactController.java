package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.Contact;
import org.personal.mason.fl.domain.repository.ContactRepository;
import org.personal.mason.fl.utils.convert.ContactConverter;
import org.personal.mason.fl.web.pojo.PoContact;
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
public class ContactController extends AbstractController{

    @Autowired
    private ContactRepository contactRepository;

    @PreAuthorize("denyAll")
    @RequestMapping(method = RequestMethod.GET,
            value = {"contact"}
    )
    public ResponseEntity<List<PoContact>> findAll() {
        List<PoContact> poContacts = ContactConverter.fromModel(contactRepository.findAll());
        if (poContacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poContacts, HttpStatus.OK);
        }
    }

    @PreAuthorize("permitAll")
    @RequestMapping(method = RequestMethod.GET,
            value = {"contact/{id}"}
    )
    public ResponseEntity<PoContact> findById(@PathVariable Long id) {
        PoContact poContact = ContactConverter.fromModel(contactRepository.findOne(id));
        if (poContact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poContact, HttpStatus.OK);
        }
    }

    @PreAuthorize("denyAll")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"contact"}
    )
    public ResponseEntity<PoContact> createContact(@RequestBody PoContact poContact, UriComponentsBuilder builder) {

        PoContact result = ContactConverter.fromModel(contactRepository.save(ContactConverter.toModel(poContact)));

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("contact/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"contact/{id}"}
    )
    public ResponseEntity updateRole(@PathVariable Long id, @RequestBody PoContact poContact, UriComponentsBuilder builder) {
        Contact contact = ContactConverter.toModel(poContact);
        Contact model = contactRepository.findOne(id);
        updateModel(model, contact);
        Contact result = contactRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("contact/" + result.getId()).build().toUri());
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"contact/{id}"}
    )
    public ResponseEntity<PoContact> partialUpdateRole(@PathVariable Long id, @RequestBody PoContact poContact, UriComponentsBuilder builder) {
        Contact contact = ContactConverter.toModel(poContact);
        Contact model = contactRepository.findOne(id);
        mergeModel(model, contact);
        Contact result = contactRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("contact/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("denyAll")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"contact/{id}"}
    )
    public ResponseEntity<PoContact> delete(@PathVariable Long id) {
        try {
            contactRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}