package com.example.testlogin.project_java2.controller.api.user;


import com.example.testlogin.project_java2.dto.BankAccountDto;
import com.example.testlogin.project_java2.dto.UserResponseDto;
import com.example.testlogin.project_java2.model.BankAccount;
import com.example.testlogin.project_java2.model.UserAccount;
import com.example.testlogin.project_java2.security.middleware.JWTAuthenticationFilter;
import com.example.testlogin.project_java2.service.BankAccountService;
import com.example.testlogin.project_java2.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserGetApiController {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final BankAccountService bankAccountService;

    @Autowired
    public UserGetApiController(JWTAuthenticationFilter jwtAuthenticationFilter, UserService userService, BankAccountService bankAccountService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/profile")
    private ResponseEntity<?> user(
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request
    ){
        JSONObject object = new JSONObject();
        try{
            String token = jwtAuthenticationFilter.getJWTFromRequest(request);
            String userId = userService.findByEmail(userDetails.getUsername()).getId();
            UserResponseDto auth = new UserResponseDto(userId, token, userDetails);

            System.out.println("Get user login: " + auth);

            return new ResponseEntity<>(auth, HttpStatus.OK);
        }catch (Exception exception){
            object.put("error",exception);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @GetMapping("/bank_account")
    private ResponseEntity<?> bank_account(
            @AuthenticationPrincipal UserDetails userDetails){
        JSONObject res = new JSONObject();
        try{
            UserAccount user = userService.findByEmail(userDetails.getUsername());
            BankAccountDto bankAccountDto = bankAccountService.getBankAccountDtoByUser(user);

            return new ResponseEntity<>(bankAccountDto, HttpStatus.OK);
        }catch (Exception exception){
            res.put("error",exception);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }






}
