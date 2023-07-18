package com.simform.testing.repository;

import com.simform.testing.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;
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
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
    }

    @AfterEach
    void tearDown() {
        employee1 = null;
        employee2 = null;
        employeeRepository.deleteAll();
    }

    @Test
    public void testSave() {
        Employee employee3 = Employee.builder()
                .firstName("yash")
                .lastName("solanki")
                .email("yash2643@gmail.com")
                .stack("java")
                .id(3).build();
        Employee save = employeeRepository.save(employee3);
        assertThat(save).isEqualTo(employee3);
    }

    @Test
    public void testDeleteAll() {
        employeeRepository.deleteAll();
        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    public void testDeleteById() {
        employeeRepository.deleteById(1);
        Optional<Employee> byId = employeeRepository.findById(1);
        assertThat(byId.isPresent()).isFalse();
    }
}