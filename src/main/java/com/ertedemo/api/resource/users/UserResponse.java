package com.ertedemo.api.resource.users;

import com.ertedemo.domain.model.entites.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Integer age;
    private String gender="";
    private String description = "";
    private Float rankPoints = 5.0f;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.description = user.getDescription();
        this.rankPoints = user.getRankPoints();
    }
}
