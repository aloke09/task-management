package com.bridgelabz.UserManagement1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private int id;

    @NotNull
    private String username;

    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @NotNull
    private String password;

    @Column(name = "tasks")
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_u_id",referencedColumnName = "u_id")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnoreProperties("user")  // Prevent infinite recursion
    private List<Task> tasks;
}
