package com.bridgelabz.UserManagement1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task
{
    @Id
    private int id;

    @NotNull
    private String title;

    @NotBlank
    private String description;

//    @NotEmpty
    private LocalDate dueDate;

    @Pattern(regexp = "(Pending|In\\sProgress|Completed)")
    private String status;//pending or in progress or completed

    @Pattern(regexp = "(High|Medium|Low)")
    private String priority;//high medium low

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "c_id")
//    private Category category;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
