package com.example.testlogin.project_java2.controller.api.admin;

import com.example.testlogin.project_java2.dto.BankAccountDto;
import com.example.testlogin.project_java2.dto.UserDto;
import com.example.testlogin.project_java2.dto.UserResponseDto;
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
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminGetApiController {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final BankAccountService bankAccountService;

    @Autowired
    public AdminGetApiController(JWTAuthenticationFilter jwtAuthenticationFilter, UserService userService, BankAccountService bankAccountService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/profile")
    private ResponseEntity<JSONObject> admin(
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request
    ){
        JSONObject object = new JSONObject();
        try{
            String token = jwtAuthenticationFilter.getJWTFromRequest(request);
            String userId = userService.findByEmail(userDetails.getUsername()).getId();
            UserResponseDto auth = new UserResponseDto(userId, token, userDetails);

            object.put("user", auth);

            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception exception){

            object.put("error",exception);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @GetMapping("/users")
    private ResponseEntity <JSONObject> users(){

        try{
            JSONObject object = new JSONObject();
            List<UserDto> userDtos = userService.listUsersApi();
            object.put("users",userDtos);

            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception exception){

            JSONObject object = new JSONObject();
            object.put("error",exception);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @GetMapping("/bank_accounts")
    private ResponseEntity <JSONObject> bank_accounts(){

        try{
            JSONObject object = new JSONObject();
            List<BankAccountDto> bankAccountDtoList = bankAccountService.listBankAccountApi();
            object.put("bank_accounts",bankAccountDtoList);

            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception exception){

            JSONObject object = new JSONObject();
            object.put("error",exception);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }
}
