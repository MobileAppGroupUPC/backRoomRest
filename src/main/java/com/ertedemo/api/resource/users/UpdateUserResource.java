package com.ertedemo.api.resource.users;

import lombok.Data;

@Data
public class UpdateUserResource {
    private Long id;
    private String name;

    private String lastName;

    private Integer age;

    private String email;

    private String gender;

    private String description;

}
