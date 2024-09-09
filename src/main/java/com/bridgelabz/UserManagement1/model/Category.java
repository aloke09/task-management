package com.bridgelabz.UserManagement1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category
{
    @Id
//    @Column(name = "c_id")
//    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @Column(name = "tasks")
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_c_id",referencedColumnName = "c_id")
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Task> task;
}
