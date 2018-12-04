package com.dunn.aspects.sessionmanagement;

import org.springframework.web.bind.support.SessionStatus;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeSessionAttributesToViews {

   String sessionStatus();

   Class<? extends SessionStatus> sessionStatusType() default SessionStatus.class;

   String[] allowedViewNames() default {};

   String[] sessionAttribute();

}
