package com.example.testlogin.project_java2.controller.api;


import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GetApiController {


    @GetMapping("/hello")
    private ResponseEntity <JSONObject> index(){

        List<String> list = new ArrayList<>();
        list.add("toi la tuong");
        JSONObject object = new JSONObject();
        object.put("message","Hello world!");
        object.put("owner",list);

        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
