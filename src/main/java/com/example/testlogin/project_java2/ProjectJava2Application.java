package com.example.testlogin.project_java2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectJava2Application {

    public static void main(String[] args) {


        SpringApplication.run(ProjectJava2Application.class, args);
        System.out.println("Running...");


    }



}
