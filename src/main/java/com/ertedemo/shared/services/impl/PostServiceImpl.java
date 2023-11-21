package com.ertedemo.shared.services.impl;

import com.ertedemo.domain.model.entites.Post;
import com.ertedemo.domain.model.entites.User;
import com.ertedemo.domain.persistence.PostRepository;
import com.ertedemo.domain.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getById(Long postId) {
        return Optional.of(postRepository.getById(postId));
    }

    @Override
    public Optional<Post> create(Post post) {
        return Optional.of(postRepository.save(post));
    }

    @Override
    public Optional<Post> update(Post post) {
        return Optional.of(postRepository.save(post));
    }

    @Override
    public ResponseEntity<?> delete(Long postId) {
        return postRepository.findById(postId).map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    @Override
    public List<Post> getByAuthor(User author) {
        return postRepository.findAllByAuthor(author);
    }
}
