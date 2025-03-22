package ru.kpfu.itis.kononenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.kononenko.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCity(String city);
    List<Comment> findByAuthorName(String authorName);
}
