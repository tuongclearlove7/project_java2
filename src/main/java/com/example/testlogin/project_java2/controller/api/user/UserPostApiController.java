package com.example.testlogin.project_java2.controller.api.user;

import com.example.testlogin.project_java2.dto.BankAccountDto;
import com.example.testlogin.project_java2.model.UserAccount;
import com.example.testlogin.project_java2.security.Security;
import com.example.testlogin.project_java2.security.middleware.JWTAuthenticationFilter;
import com.example.testlogin.project_java2.service.BankAccountService;
import com.example.testlogin.project_java2.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserPostApiController {


    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final BankAccountService bankAccountService;

    @Autowired
    public UserPostApiController(JWTAuthenticationFilter jwtAuthenticationFilter, UserService userService, BankAccountService bankAccountService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/active_bank_account")
    private ResponseEntity<JSONObject> create_bank_account(){

        JSONObject object = new JSONObject();
        try{
            UserAccount user = userService.findByEmail(Security.getSessionUser());
            BankAccountDto bankAccountDto =  bankAccountService.active_bank_account(user);
            object.put("bankAccount",bankAccountDto);

            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception exception){
            object.put("error", exception.getMessage());
            return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
