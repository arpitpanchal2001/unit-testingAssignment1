package com.simform.testing;

import com.simform.testing.entity.Employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SpringBootApplication
public class TestingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
