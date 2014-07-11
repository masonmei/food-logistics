package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.Comment;
import org.personal.mason.fl.domain.repository.CommentRepository;
import org.personal.mason.fl.utils.convert.CommentConverter;
import org.personal.mason.fl.web.pojo.PoComment;
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
public class CommentController extends AbstractController {

    @Autowired
    private CommentRepository commentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET,
            value = {"comment"}
    )
    public ResponseEntity<List<PoComment>> findAll() {
        List<PoComment> poComments = CommentConverter.fromModel(commentRepository.findAll());
        if (poComments.isEmpty()) {
            return new ResponseEntity<List<PoComment>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(poComments, HttpStatus.OK);
        }
    }

    @PreAuthorize("permitAll")
    @RequestMapping(method = RequestMethod.GET,
            value = {"comment/{id}"}
    )
    public ResponseEntity<PoComment> findById(@PathVariable Long id) {
        PoComment poComment = CommentConverter.fromModel(commentRepository.findOne(id));
        if (poComment == null) {
            return new ResponseEntity<PoComment>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<PoComment>(poComment, HttpStatus.OK);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"comment"}
    )
    public ResponseEntity<PoComment> createComment(@RequestBody PoComment poComment, UriComponentsBuilder builder) {

        PoComment result = CommentConverter.fromModel(commentRepository.save(CommentConverter.toModel(poComment)));

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("comment/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"comment/{id}"}
    )
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody PoComment poComment, UriComponentsBuilder builder) {
        Comment comment = CommentConverter.toModel(poComment);
        Comment model = commentRepository.findOne(id);
        updateModel(model, comment);
        Comment result = commentRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("comment/" + result.getId()).build().toUri());
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"comment/{id}"}
    )
    public ResponseEntity partialUpdateComment(@PathVariable Long id, @RequestBody PoComment poComment, UriComponentsBuilder builder) {
        Comment comment = CommentConverter.toModel(poComment);
        Comment model = commentRepository.findOne(id);
        mergeModel(model, comment);
        Comment result = commentRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("comment/" + result.getId()).build().toUri());
            return new ResponseEntity(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"comment/{id}"}
    )
    public ResponseEntity<PoComment> delete(@PathVariable Long id) {
        try {
            commentRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}