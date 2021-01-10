package com.codeup.betterreads.validation;

import javax.validation.*;

import com.codeup.betterreads.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserService userService;

    public void initialize (UniqueUsername uniqueUsername) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !userService.isUsernameAlreadyInUse(value);
    }

}
