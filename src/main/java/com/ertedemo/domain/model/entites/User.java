package com.ertedemo.domain.model.entites;

import com.ertedemo.api.resource.users.CreateUserResource;
import com.ertedemo.api.resource.users.UpdateUserResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    @NotNull
    @Size(min=2, max=50)
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Integer age = 18;

    @NotNull
    private String gender="";

    @NotNull
    private String description = "";

    @Max(value = 5)
    private Float rankPoints = 5.0f;

    public User(CreateUserResource resource){
        this.name = resource.getName();
        this.lastName=resource.getLastName();
        this.email = resource.getEmail();
        this.password = resource.getPassword();
    }
    public void updateUser(UpdateUserResource resource){
        this.name = resource.getName();
        this.lastName = resource.getLastName();
        this.age = resource.getAge();
        this.email = resource.getEmail();
        this.gender = resource.getGender();
        this.description = resource.getDescription();
    }
}
