package com.dunn.aspects.sessionmanagement;

import com.dunn.model.WoodProject;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManageCreateWoodProjectSession {

   String sessionStatus();

   Class<? extends SessionStatus> sessionStatusType() default SessionStatus.class;

   String[] allowedViewNames() default {};

}
