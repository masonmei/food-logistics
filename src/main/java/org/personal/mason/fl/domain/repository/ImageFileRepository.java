package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 6/25/14.
 */
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
}
