package com.bridgelabz.UserManagement1.exception;

import com.bridgelabz.UserManagement1.model.Category;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handelValidation(MethodArgumentNotValidException ex)
    {
        Map<String,String> err=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
                err.put(error.getField(),error.getDefaultMessage()
                ));
        return err;
    }

    @ExceptionHandler(CustomeException.class)
    public String emailNotFound(CustomeException ex)
    {

        System.out.println("Custom Exception Occurred: " + ex.getMessage());
        return ex.getMessage();
    }

//    @ExceptionHandler(CustomeException.class)
//    public Category categoryNotFound(CustomeException ex)
//    {
////        responseDTO<Employee> errorResponse = new responseDTO<>(
////                HttpStatus.BAD_REQUEST.name(),
////                ex.getMessage(),
////                null
////        );
////        System.out.println("Custom Exception Occurred: " + ex.getMessage());
////        return errorResponse;
//        Category c=new Category();
//        c.setName(ex.getMessage());
//        return c;
//    }
}
