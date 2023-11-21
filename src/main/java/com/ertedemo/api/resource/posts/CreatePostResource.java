package com.ertedemo.api.resource.posts;

import lombok.Data;

@Data
public class CreatePostResource {
    private String title;

    private String description;

    private String characteristics;

    private String location;

    private Float price;

    private String category;

    private Long author_id;

}
