package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.domain.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/25/14.
 */
public class AttachedFiles {
    private MultipartFile[] attachment;

    public MultipartFile[] getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile[] attachment) {
        this.attachment = attachment;
    }

    public List<ImageFile> toImageFiles() {
        List<ImageFile> files = new ArrayList<>();
        if (attachment != null) {
            for (MultipartFile multipartFile : attachment) {
                if (!multipartFile.isEmpty()) {
                    ImageFile imageFile = new ImageFile();
                    imageFile.setName(multipartFile.getOriginalFilename());
                    imageFile.setType(multipartFile.getContentType());
                    imageFile.setSize(multipartFile.getSize());
                    try {
                        imageFile.setContent(multipartFile.getBytes());
                    } catch (IOException e) {
                        continue;
                    }
                    files.add(imageFile);
                }
            }
        }
        return files;
    }
}
