package com.ertedemo.api.rest;

import com.ertedemo.api.resource.posts.CreatePostResource;
import com.ertedemo.api.resource.posts.PostResponse;
import com.ertedemo.api.resource.posts.UpdatePostResource;
import com.ertedemo.domain.model.entites.Post;
import com.ertedemo.domain.model.entites.User;
import com.ertedemo.domain.services.PostService;
import com.ertedemo.domain.services.UserService;
import com.ertedemo.shared.services.media.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final StorageService storageService;
    private final HttpServletRequest request;

    @GetMapping
    public List<PostResponse> getAllPost() {
        List<PostResponse> postResponses = postService.getAll().stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());

        Collections.reverse(postResponses);

        return postResponses;
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {

        Optional<Post> post = postService.getById(postId);

        if(post.isEmpty())
            return  ResponseEntity.badRequest().build();

        return ResponseEntity.ok(new PostResponse(post.get()));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<PostResponse>> getPostsByAuthorId(@PathVariable Long authorId) {

        Optional<User> user = userService.getById(authorId);

        if (user.isEmpty())
            return ResponseEntity.badRequest().build();

        List<PostResponse> responseList = postService.getByAuthor(user.get()).stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody CreatePostResource postResource) {

        Optional<User> author =userService.getById(postResource.getAuthor_id());

        if (author.isPresent()) {

            Post post = new Post(author.get(), postResource);
            postService.create(post);

            return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(post));
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping()
    public ResponseEntity<PostResponse> updatePost(@RequestBody UpdatePostResource resource) {
        Optional<Post>post = postService.getById(resource.getId());
        if(post.isEmpty())
            return ResponseEntity.badRequest().build();

        post.get().updatePost(resource);
        Optional<Post>postUpdated = postService.update(post.get());

        if (!postUpdated.isPresent())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.ok(new PostResponse(postUpdated.get()));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postService.delete(postId);
    }

    //EXTRA METHODOS

}