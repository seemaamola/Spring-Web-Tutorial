package com.example.springbootwebtutorial.controllers;

import com.example.springbootwebtutorial.dto.EmployeeDTO;
import com.example.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController/// ---- Mapping defined inside this controller are rest in nature
@RequestMapping(path ="/employees") // parent path
public class EmployeeController {

    private final EmployeeService employeeService;

    //constructor to initialize EmployeeService employeeService
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Get one employee by ID
    @GetMapping("{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeId(@PathVariable(name = "employeeID") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeId(id);

        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all Employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {

        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody  @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeByID(employeeId, employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean gotDeleted = employeeService.deleteEmployeeByID(employeeId);

        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();


    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId) {

        EmployeeDTO employeeDTO= employeeService.updatePartialEmployeeByID(employeeId, updates);

        if(employeeDTO==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);

    }
}