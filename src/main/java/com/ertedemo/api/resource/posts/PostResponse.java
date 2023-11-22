package com.ertedemo.api.resource.posts;

import com.ertedemo.domain.model.entites.Post;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String description;
    private String characteristics;
    private String location;
    private Float price;
    private List<String> imageUrls;
    private String category;
    private Long author_id;

    public PostResponse(Post post) {
        this.imageUrls = new ArrayList<>();

        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.characteristics = post.getCharacteristics();
        this.location = post.getLocation();
        this.price = post.getPrice();

        List<String> postImageUrls = post.getImageUrls();
        if (postImageUrls != null && !postImageUrls.isEmpty()) {
            this.imageUrls.addAll(postImageUrls);
        } else {
            this.imageUrls.add("https://images.pexels.com/photos/1396122/pexels-photo-1396122.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        }

        this.category = post.getCategory();
        this.author_id = post.getAuthor().getId();
    }
}
