package com.example.springbootwebtutorial.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity        // tell hibernate that this is the java class that you need to convert to table in db
@Table(name = "employee")   //  give table a name and constraints
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode   // not recommended
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfJoining;
    private Boolean isActive;
    private String role;
    private Double salary;
    // these fields inside this entity will convert to columns inside the table

}
