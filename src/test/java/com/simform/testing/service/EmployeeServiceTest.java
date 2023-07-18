package com.simform.testing.service;

import com.simform.testing.entity.Employee;
import com.simform.testing.exception.EmployeeNotFound;
import com.simform.testing.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;
    Employee employee1;
    Employee employee2;


    @BeforeEach
    void setUp() {
        employee1 = Employee.builder()
                .firstName("Arpit")
                .lastName("Panchal")
                .email("arpit2643@gmail.com")
                .stack("java")
                .id(1).build();
        employee2 = Employee.builder()
                .firstName("Parth")
                .lastName("solanki")
                .email("parth2643@gmail.com")
                .stack("java")
                .id(2).build();
    }

    @AfterEach
    void tearDown() {
        employee1 = null;
        employee2 = null;
        employeeRepository.deleteAll();
    }

    @Test
    void testCreate() {
        when(employeeRepository.save(employee1)).thenReturn(employee1);
        Employee employee = employeeService.create(employee1);
        assertThat(employee).isEqualTo(employee1);
        assertThat(employee.getId()).isEqualTo(employee1.getId());
        assertThat(employee.getFirstName()).isEqualTo(employee1.getFirstName());
    }

    @Test
    void testFindByIdFound() {

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));
        Employee byId = employeeService.findById(1);
        assertThat(byId.getFirstName()).isEqualTo(employee1.getFirstName());
        assertThat(byId.getId()).isEqualTo(employee1.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(employeeRepository.findById(10)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFound.class, () -> employeeService.findById(10));
    }

    @Test
    void testFindAllFound() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.findAll()).thenReturn(employees);
        assertThat(employeeService.findAll().size()).isEqualTo(employees.size());
        assertThat(employeeService.findAll().get(0).getFirstName()).isEqualTo(employees.get(0).getFirstName());

    }

    @Test
    void testFindAllNotFound() {
        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employees);
        assertThrows(EmployeeNotFound.class, () -> employeeService.findAll());

    }

    @Test
    void testDeleteByIdFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));
        Employee employee = employeeService.deleteById(1);
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(employee1.getId());
        verify(employeeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFound.class, () -> employeeService.deleteById(1));
    }

    @Test
    void testDeleteAllFound() {
        //Given
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeRepository.findAll()).thenReturn(employees);
        //when
        String resultMessage = employeeService.deleteAll();
        //Then
        assertThat(resultMessage).isEqualTo("Total 2 Employees has been deleted");
        verify(employeeRepository, times(1)).findAll();
        verify(employeeRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteAllNotFound() {
        //Given
        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employees);
        assertThrows(EmployeeNotFound.class, () -> employeeService.deleteAll());
    }

    @Test
    void testUpdateFound() {
        Employee updatedEmployee = Employee.builder()
                .id(1)
                .firstName("ARPIT")
                .lastName("Panchal")
                .email("Arpit2643@gmail.com")
                .stack("java")
                .build();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        Employee update = employeeService.update(1, updatedEmployee);
        assertThat(update.getId()).isEqualTo(updatedEmployee.getId());
        assertThat(update.getFirstName()).isEqualTo(updatedEmployee.getFirstName());
        assertThat(update.getLastName()).isEqualTo(updatedEmployee.getLastName());
    }

    @Test
    void testUpdateNotFound() {
        Employee updatedEmployee = Employee.builder()
                .id(1)
                .firstName("ARPIT")
                .lastName("Panchal")
                .email("Arpit2643@gmail.com")
                .stack("java")
                .build();
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFound.class, () -> employeeService.update(1, updatedEmployee));

    }
}