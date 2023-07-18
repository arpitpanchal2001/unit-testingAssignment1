package com.simform.testing.service;

import com.simform.testing.entity.Employee;
import com.simform.testing.exception.EmployeeNotFound;
import com.simform.testing.exception.InvalidId;
import com.simform.testing.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee != null) {
            employeeRepository.save(employee);
            return employee;
        } else {
            throw new RuntimeException("employee should not be null");
        }
    }

    public Employee findById(int id) {

        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new EmployeeNotFound("There is no Employee with" + id);
        }
    }

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFound("There are no Employees");
        } else {
            return employees;
        }
    }

    public Employee deleteById(int id) {

        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            Employee employee = byId.get();
            employeeRepository.deleteById(id);
            return employee;
        } else {
            throw new EmployeeNotFound("Employee not Found");
        }

    }

    public String deleteAll() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFound("There are no Employees");
        } else {
            employeeRepository.deleteAll();
            return "Total " + employees.size() + " Employees has been deleted";
        }
    }

    public Employee update(int id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee exestingEmployee = optionalEmployee.get();
            exestingEmployee.setFirstName(employee.getFirstName());
            exestingEmployee.setLastName(employee.getLastName());
            exestingEmployee.setEmail(exestingEmployee.getEmail());
            exestingEmployee.setStack(employee.getStack());
            employeeRepository.save(exestingEmployee);
            return exestingEmployee;
        } else {
            throw new EmployeeNotFound("employee not found with id = " + id);
        }

    }
}

