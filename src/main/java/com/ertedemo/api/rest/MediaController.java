package com.ertedemo.api.rest;

import com.ertedemo.api.resource.posts.PostResponse;
import com.ertedemo.domain.model.entites.Post;
import com.ertedemo.domain.services.PostService;
import com.ertedemo.shared.services.media.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "api/media")
@AllArgsConstructor
public class MediaController {

    private final StorageService storageService;
    private final HttpServletRequest request;
    private final PostService postService;

    @PostMapping("/post/{postId}/upload")
    public ResponseEntity<PostResponse> uploadFiles(
            @PathVariable Long postId,
            @RequestParam("files") List<MultipartFile> files) {


        Optional<Post> post = postService.getById(postId);

        if (post.isEmpty())
            return ResponseEntity.notFound().build();

        List<String> fileUrls = new ArrayList<>();

        int filesToProcess = Math.min(files.size(), 3);

        for (int i = 0; i < filesToProcess; i++) {
            MultipartFile file = files.get(i);
            String path = storageService.storage(file);
            String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            String url = ServletUriComponentsBuilder
                    .fromHttpUrl(host)
                    .path("/api/media/")
                    .path(path)
                    .toUriString();

            fileUrls.add(url);
        }
        post.get().setImageUrls(fileUrls);
        Optional<Post> postWithImages= postService.update(post.get());

        //return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(postWithImages.get()));
        return ResponseEntity.ok(new PostResponse(postWithImages.get()));
    }


    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

}
