package com.codeup.betterreads;

import javax.validation.*;

import com.codeup.betterreads.services.UserDetailsLoader;
import com.codeup.betterreads.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    public void initialize (UniqueEmail uniqueEmail) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !userService.isEmailAlreadyInUse(value);
    }

}
