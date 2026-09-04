package com.example.springbootwebtutorial.dto;

import com.example.springbootwebtutorial.annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of the employee cannot be blank")
    @Size(min= 3, max = 10, message = "Number of characters in name should be in the range:[3,10]")
    private String name;
    @Email(message = "Email should be a valid email")
    private String email;
    @Min(value=18,message = "Age of Employee cannot be less than 18")
    @Max(value=60,message = "Age of Employee cannot be greater than 80")
    private Integer age;
    @NotBlank(message = "role of employee cannot be blank")
    //@Pattern(regexp = "^(ADMIN|USER)$") //ADMIN,USER
    @EmployeeRoleValidation
    private String role;
    @NotNull(message = "Salary of employee cannot be null")
    @Positive(message = "Salary of employee cannot be negative")
    @Digits(integer = 6,fraction = 2, message = "The salary can be in the form: XXXXXX:YY")
    @DecimalMax(value="100000.99")
    @DecimalMin(value= "100.50")
    private Double salary;
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private Boolean isActive;


}
