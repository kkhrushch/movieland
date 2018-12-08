package com.khrushch.movieland.rest.v1.annotation;

import com.khrushch.movieland.model.UserRole;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProtectedBy {

    UserRole[] acceptedRoles() default {};

}
