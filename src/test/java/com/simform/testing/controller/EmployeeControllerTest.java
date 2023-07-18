package com.simform.testing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simform.testing.entity.Employee;
import com.simform.testing.exception.EmployeeNotFound;
import com.simform.testing.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @MockBean
    EmployeeService employeeService;
    @Autowired
    MockMvc mockMvc;
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
    }

    @Test
    void testCreateFound() throws Exception {
        when(employeeService.create(employee1)).thenReturn(employee1);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(employee1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void testCreateNotFound() throws Exception {
        when(employeeService.create(employee1)).thenThrow(new EmployeeNotFound("employee should not be null"));
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(employee1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void testFindByIdFound() throws Exception {
        when(employeeService.findById(1)).thenReturn(employee1);
        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = objectMapper.writeValueAsString(employee1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(employeeService.findById(10)).thenThrow(new EmployeeNotFound("Employee not found"));
        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = objectMapper.writeValueAsString(employee1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllFound() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        when(employeeService.findAll()).thenReturn(employees);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void testFindAllNotFound() throws Exception {
        when(employeeService.findAll()).thenThrow(new EmployeeNotFound("There are no Employees stored"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void update() throws Exception {
        Employee updateEmployee = Employee.builder()
                .id(3)
                .firstName("nimit")
                .lastName("solanki")
                .stack("java")
                .email("nimit@gmial.com").build();
        when(employeeService.update(3, updateEmployee)).thenReturn(updateEmployee);
        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = objectMapper.writeValueAsString(updateEmployee);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andDo(print())
                .andExpect(status().isOk());

    }
}