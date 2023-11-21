package com.ertedemo.api.resource.users;

import lombok.Data;

@Data
public class LoginCredential {
    private String email;
    private String password;
}
