package com.codeup.betterreads.validation;

import java.lang.annotation.*;
import javax.validation.*;

//Constraint: indicates what class is implementing the constraint for validation, more about it is covered in next part of the post,
@Constraint(validatedBy = UniqueUsernameValidator.class)
//Retention: in short, it indicates how long annotation will be making impact on our code (before or after compilation), in below case — RetentionPolicy.RUNTIME — it means that this annotation will be available after the runtime,
@Retention(RetentionPolicy.RUNTIME)
//Target: indicates where this annotation can be applied, i.e. on a class, field, method.
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface UniqueUsername {

    public String message() default "There is already a user with this username!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}