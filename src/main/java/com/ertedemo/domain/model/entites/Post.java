package com.ertedemo.domain.model.entites;

import com.ertedemo.api.resource.posts.CreatePostResource;
import com.ertedemo.api.resource.posts.UpdatePostResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String characteristics;

    @NotNull
    @NotBlank
    private String location;

    @NotNull
    private Float price;

    @ElementCollection
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @NotNull
    @NotBlank
    private String category;

    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    private User renter;

    public Post(User author, CreatePostResource resource){
        this.title = resource.getTitle();
        this.description = resource.getDescription();
        this.characteristics = resource.getCharacteristics();
        this.location = resource.getLocation();
        this.price = resource.getPrice();
        this.category = resource.getCategory();
        this.author = author;
    }
    public void updatePost(UpdatePostResource resource){
        this.title = resource.getTitle();
        this.description= resource.getDescription();
        this.characteristics=resource.getCharacteristics();
        this.location=resource.getLocation();
        this.price= resource.getPrice();
        this.category= resource.getCategory();
    }

}