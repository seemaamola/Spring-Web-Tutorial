package com.example.springbootwebtutorial.controllers;

import com.example.springbootwebtutorial.dto.EmployeeDTO;
import com.example.springbootwebtutorial.services.EmployeeService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController/// ---- Mapping defined inside this controller are rest in nature
@RequestMapping(path ="/employees") // parent path
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Get one employee by ID
    @GetMapping("{employeeID}")
    public EmployeeDTO getEmployeeId(@PathVariable(name ="employeeID")Long id)
    {
        return employeeService.getEmployeeId(id);

    }

    // Get all Employees
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false, name="inputAge") Integer age,
                                @RequestParam(required = false) String sortBy)
    {
       return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee)
    {
        return employeeService.createNewEmployee(inputEmployee);
    }

    @PutMapping(path="/{employeeId}")
     public EmployeeDTO updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId)
    {
        return employeeService.updateEmployeeByID(employeeId,employeeDTO);
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployeeById(@PathVariable Long employeeId)
    {
        employeeService.deleteEmployeeByID(employeeId);
    }

}