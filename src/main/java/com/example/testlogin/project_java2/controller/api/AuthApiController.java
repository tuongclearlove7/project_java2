package com.example.testlogin.project_java2.controller.api;
import com.example.testlogin.project_java2.dto.UserDto;
import com.example.testlogin.project_java2.dto.UserResponseDto;
import com.example.testlogin.project_java2.security.JWTGeneratorToken;
import com.example.testlogin.project_java2.security.middleware.JWTAuthenticationFilter;
import com.example.testlogin.project_java2.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/v1")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTGeneratorToken JWTGeneratorToken;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public AuthApiController(AuthenticationManager authenticationManager, UserService userService, com.example.testlogin.project_java2.security.JWTGeneratorToken jwtGeneratorToken, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.JWTGeneratorToken = jwtGeneratorToken;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @PostMapping("/login")
    private ResponseEntity<JSONObject> login(@RequestBody UserDto userDto){

        JSONObject object = new JSONObject();
        try{
            Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JWTGeneratorToken.generateToken(authentication);
            Object principal = authentication.getPrincipal();
            String userId = userService.findByEmail(authentication.getName()).getId();
            UserResponseDto auth = new UserResponseDto(userId, token, principal);
            object.put("user_login", auth);

            return new ResponseEntity<>(object, HttpStatus.OK);
        }catch (Exception error){

            object.put("error", "Invalid username or password!");
            return new ResponseEntity<>(object, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/logout")
    ResponseEntity<JSONObject> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

        logoutHandler.logout(request, response, authentication);
        JSONObject object = new JSONObject();
        object.put("message","logout successfully");

        return new ResponseEntity<>(object, HttpStatus.OK);
    }





}
