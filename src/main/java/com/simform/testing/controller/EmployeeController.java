package com.simform.testing.controller;

import com.simform.testing.entity.Employee;
import com.simform.testing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee employee1 = employeeService.create(employee);
        return new ResponseEntity<>(employee1, HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> findById(@PathVariable("id") int id) {
        Employee employee = employeeService.findById(id);
        return new ResponseEntity<>(employee, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employeeList = employeeService.findAll();
        return new ResponseEntity<>(employeeList, HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteById(@PathVariable("id") int id) {
        Employee employee = employeeService.deleteById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        String employees = employeeService.deleteAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> update(@RequestBody Employee employee, @PathVariable int id) {
        Employee updatedEmployee = employeeService.update(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}

