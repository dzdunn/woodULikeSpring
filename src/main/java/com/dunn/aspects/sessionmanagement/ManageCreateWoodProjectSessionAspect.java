package com.dunn.aspects.sessionmanagement;

import com.dunn.config.session.SessionNavigation;
import com.dunn.controller.path.ViewName;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Aspect
public class ManageCreateWoodProjectSessionAspect {

    @Autowired
    private SessionNavigation sessionNavigation;


    @Pointcut("@annotation(ManageCreateWoodProjectSession)")
    public void callShowCreateWoodProject(){

    }

    @Before("callShowCreateWoodProject()")
    public void beforeShowCreateWoodProject(JoinPoint joinPoint){
        sessionNavigation.getLastView();
        if (!sessionNavigation.getLastView().equals("redirect:" + ViewName.CREATE_WOOD_PROJECT)){
            RequestContextHolder.currentRequestAttributes().removeAttribute("woodProjectDTO", RequestAttributes.SCOPE_SESSION);

        }


    }

    @AfterReturning(value = "callShowCreateWoodProject()", returning = "returnValue")
    public void afterShowCreateWoodProject(JoinPoint joinPoint, Object returnValue){


        if(returnValue instanceof String){

        }
    }

}
