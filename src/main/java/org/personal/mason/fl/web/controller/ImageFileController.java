package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.repository.ImageFileRepository;
import org.personal.mason.fl.utils.convert.ConvertType;
import org.personal.mason.fl.utils.convert.ImageFileConverter;
import org.personal.mason.fl.web.pojo.PoImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by mason on 6/25/14.
 */
@Controller
public class ImageFileController {

    @Autowired
    private ImageFileRepository imageFileRepository;

//    @PreAuthorize("denyAll")
    @RequestMapping(
            value = {"image"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResponseEntity<List<PoImageFile>> getImages(){
        List<PoImageFile> imageFiles = ImageFileConverter.fromModel(imageFileRepository.findAll());

        if(imageFiles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(imageFiles, HttpStatus.OK);
        }
    }

//    @PreAuthorize("permitAll")
    @RequestMapping(
            value = {"image/{id}"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable Long id){
        PoImageFile imageFile = ImageFileConverter.fromModel(imageFileRepository.findOne(id), ConvertType.FULL);

        if(imageFile == null){
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(imageFile.getType()));
            headers.setContentLength(imageFile.getSize());
            return new ResponseEntity<byte[]>(imageFile.getContent(), headers, HttpStatus.OK);
        }
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @RequestMapping(value = {"image/upload"}, method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(AttachedFiles attachedFiles) {
        List<PoImageFile> poImageFiles = ImageFileConverter.fromModel(imageFileRepository.save(attachedFiles.toImageFiles()));
        if(poImageFiles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<>("Upload-Succeed", HttpStatus.OK);
        }
    }

//    @PreAuthorize("hasAnyRole('MERCHANT', 'ADMIN')")
    @RequestMapping(
            value = {"image/{id}"},
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> removeImage(@PathVariable Long id){
        try {
            imageFileRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
