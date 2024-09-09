package com.bridgelabz.UserManagement1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter
{
    @Autowired
    JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        String token=null;
        String username=null;
        System.out.println("inside jwt filter  do filter method");

        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            System.out.println("in bearer logic");
            username=jwtService.extractUserName(token);
            System.out.println(username);

        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            System.out.println("in userame autntication logic ---->"+username);

            UserDetails userDetails=context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails))//Validates the JWT against the user details. If valid:
            {
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request,response);


    }
}
