package com.ertedemo.api.resource.users;

import lombok.Data;

@Data
public class CreateUserResource {
    private String name;
    private String lastName;
    private String email;
    private String password;
}
