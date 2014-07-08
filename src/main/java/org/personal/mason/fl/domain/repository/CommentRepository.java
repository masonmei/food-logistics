package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 6/22/14.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
