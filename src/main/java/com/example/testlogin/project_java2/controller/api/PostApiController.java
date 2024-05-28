package com.example.testlogin.project_java2.controller.api;


import com.example.testlogin.project_java2.dto.UserDto;
import com.example.testlogin.project_java2.service.UserService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostApiController {

    UserService userService;

    @PostMapping("/create/user")
    private ResponseEntity<JSONObject> index(
            @Valid @RequestBody UserDto userDto,
            BindingResult res){

        if (res.hasErrors()) {
            JSONObject errorObject = new JSONObject();
            errorObject.put("status", "error");
            errorObject.put("errors", res.getAllErrors());
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        }
        UserDto userNew =  userService.createUser(userDto);

        JSONObject object = new JSONObject();
        object.put("user", userNew);

        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
