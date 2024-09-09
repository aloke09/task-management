package com.bridgelabz.UserManagement1.controller;

import com.bridgelabz.UserManagement1.exception.CustomeException;
import com.bridgelabz.UserManagement1.exception.GlobalExceptionHandler;
import com.bridgelabz.UserManagement1.model.User;
import com.bridgelabz.UserManagement1.responseDTO.UserDTO;
import com.bridgelabz.UserManagement1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserCtrl
{
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public UserDTO register(@RequestBody User user)
    {
        log.trace("user reg in ctrl");
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        log.trace("login");
        return service.login(user);
    }

    @GetMapping("/msg")
    public String msg()
    {
        return "hello world";//testing with jwt token
    }

    @PostMapping("/forgot")
    public String forgotPassword(@RequestBody Map<String, String> request)
    {
        String email = request.get("email");
        log.trace("forgot password");
        return service.forgot(email);
    }

    @PostMapping("/reset")
    public String reset(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, String> request)
    {
        String password = request.get("newpassword");
        log.trace("new password loaded");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token without "Bearer " prefix
            log.info("token verified");
            return service.resetPWD3(token, password);
        } else {
            log.error("token not verified");
            return "Authorization header must contain a Bearer token";
        }
    }
//    @PostMapping("/delete")
//    public String deleteUSer(User user) throws CustomeException
//    {
//        try
//        {
//            return service.deleteUser(user);
//        } catch (CustomeException e) {
//            String s = new GlobalExceptionHandler().emailNotFound(e);
//            return s;
//        }
//    }
//@PostMapping("/delete")
@DeleteMapping("/delete")
public String deleteUser(@RequestHeader("Authorization") String authHeader)
{
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7); // Extract token without "Bearer " prefix
        log.trace("token verified for deletion of users");
        return service.deleteUser(token);
    } else {
        log.error("unable to verify token for deletion operation");
        return "Authorization header must contain a Bearer token";
    }
}


}
