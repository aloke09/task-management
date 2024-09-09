package com.bridgelabz.UserManagement1.service;

import com.bridgelabz.UserManagement1.exception.CustomeException;
import com.bridgelabz.UserManagement1.model.User;
import com.bridgelabz.UserManagement1.repository.UserRepo;
import com.bridgelabz.UserManagement1.responseDTO.UserDTO;
import com.bridgelabz.UserManagement1.security.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;
@Slf4j
@Service
public class UserService
{
    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO register(User user)
    {
        if (user.getPassword() == null || user.getPassword().isEmpty())
        {
            System.out.println(user);
            System.out.println("password--------->"+user.getPassword());
            throw new IllegalArgumentException("Password cannot be null or empty");

        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = repo.save(user);
        log.info("registered in db");
        UserDTO userDTO =new UserDTO();
        userDTO.setUsername(save.getUsername());
        userDTO.setEmail(save.getEmail());
        return userDTO;
    }

    public String login(User user)
    {
        System.out.println("user --->"+user);
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        if(authentication.isAuthenticated())
        {
            log.info("credential verified!!!");
            return jwtService.generateToken(user.getEmail());
        }
        else
        {
            log.error("invalid credentials!!");
            return "login unsuccessful/failed";
        }
    }

    public String forgot(String email)
    {
        System.out.println("email passed to service layer--->"+email);
        User user = repo.findByEmail(email).orElse(null);
        if(user==null)
        {
            System.out.println("email not found");
            log.error("email not found");
            return "email not found";
        }
        String resetToken= jwtService.generateToken(email);
        log.info("forgot password token generated");
        System.out.println("reset token of email---->"+resetToken);
        return resetToken;
    }

    public String resetPWD3(String token, String password)
    {
        System.out.println("In reset password");

        if (password == null || password.trim().isEmpty())
        {
            log.error("new password cannot be null!!");
            return "Password cannot be null or empty";
        }

        // Validate the token and extract the email
        String email = jwtService.extractUserName(token);
        System.out.println("Email: " + email);

        if (email == null || repo.findByEmail(email).orElse(null) == null)
        {
            log.error("invalid token or email");
            return "Invalid token or email";
        }

        // Fetch the user
        User user = repo.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("user not found");
            return "User not found";
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(password));
        repo.save(user);
        log.info("password reset successfully");

        return "Password successfully updated";
    }
//    public String deleteUser(User user) throws CustomeException {
//        User user1 = repo.findById(user.getId()).orElse(null);
//        if(user1==null)
//        {
//            System.out.println("user is not present in DATABASE");
//            throw new CustomeException("user is not present in DATABASE");
//        }
//        repo.deleteById(user.getId());
//        return "user details deleted successfully";
//    }
    public String deleteUser(String token)
    {
        System.out.println("In delete user");

        // Validate the token and extract the email
        String email = jwtService.extractUserName(token);
        System.out.println("Email: " + email);

        if (email == null || repo.findByEmail(email).orElse(null) == null)
        {
            log.error("Invalid token or email");
            return "Invalid token or email";
        }

        // Fetch the user
        User user = repo.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found");
            return "User not found";
        }
        repo.delete(user);
        log.info("User successfully deleted");
        return "User successfully deleted";
    }

}
