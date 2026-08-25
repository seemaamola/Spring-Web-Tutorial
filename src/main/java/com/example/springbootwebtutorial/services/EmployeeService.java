package com.example.springbootwebtutorial.services;

import com.example.springbootwebtutorial.dto.EmployeeDTO;
import com.example.springbootwebtutorial.entities.EmployeeEntity;
import com.example.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeId(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class)).
                collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(toSaveEntity);

        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeByID(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);

        EmployeeEntity saveEmployeeEntity = employeeRepository.save(employeeEntity);

        return modelMapper.map(saveEmployeeEntity,EmployeeDTO.class);
    }

    public void deleteEmployeeByID(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
