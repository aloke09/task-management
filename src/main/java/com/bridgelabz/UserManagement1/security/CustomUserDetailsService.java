package com.bridgelabz.UserManagement1.security;

import com.bridgelabz.UserManagement1.model.User;
import com.bridgelabz.UserManagement1.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepo repo;

    public CustomUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User byEmail = repo.findByEmail(email).orElse(null);
        System.out.println(byEmail);
        if(byEmail==null)
        {
            System.out.println("no user available");
            throw new UsernameNotFoundException("EMAIL NOT FOUND!!");
        }

        return new CustomUserDetails(byEmail);
    }
}
