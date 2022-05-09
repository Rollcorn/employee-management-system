package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    // create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
        return ResponseEntity.ok(employee);
    }

    // update employee by id rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee newEmployee) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employee.setFirstName(newEmployee.getFirstName());
        employee.setLastName(newEmployee.getLastName());
        employee.setEmailId(newEmployee.getEmailId());

        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }

    // delete employee by id rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
