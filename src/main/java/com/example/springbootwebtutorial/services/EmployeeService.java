package com.example.springbootwebtutorial.services;

import com.example.springbootwebtutorial.dto.EmployeeDTO;
import com.example.springbootwebtutorial.entities.EmployeeEntity;
import com.example.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeId(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity,EmployeeDTO.class));
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

    public boolean deleteEmployeeByID(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if (!exists) {
            return false;
        }
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public boolean isExistsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    public EmployeeDTO updatePartialEmployeeByID(Long employeeId, Map<String, Object> updates) {
        boolean exists = isExistsById(employeeId);

        if (!exists) return null;

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);

        updates.forEach((field,value)->{
            Field fieldtobeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldtobeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldtobeUpdated,employeeEntity,value);
        });



        updates.forEach((field,value)-> {
            Field fieldUpdate = ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldUpdate,employeeEntity,value);
        });
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

        return modelMapper.map(employeeRepository.save(savedEmployeeEntity), EmployeeDTO.class);
    }
}
