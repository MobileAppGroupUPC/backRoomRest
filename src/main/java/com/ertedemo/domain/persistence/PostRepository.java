package com.ertedemo.domain.persistence;

import com.ertedemo.domain.model.entites.Post;
import com.ertedemo.domain.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post>  findAllByAuthor(User author);
}
