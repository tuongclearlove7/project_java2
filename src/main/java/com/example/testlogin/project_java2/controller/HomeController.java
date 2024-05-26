package com.example.testlogin.project_java2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {



    @GetMapping(value = {"/"})
    private String index(){

        return "redirect:/api/v1/hello";
    }




}
