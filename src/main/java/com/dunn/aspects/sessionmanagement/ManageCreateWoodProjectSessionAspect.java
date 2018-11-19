package com.dunn.aspects.sessionmanagement;

import com.dunn.config.session.SessionNavigation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;

@Component
@Aspect
public class ManageCreateWoodProjectSessionAspect {

    @Autowired
    private SessionNavigation sessionNavigation;


    @Pointcut("@annotation(com.dunn.aspects.sessionmanagement.ScopeSessionAttributesToViews)")
    public void callShowCreateWoodProject() {

    }

    @Pointcut(value = "@annotation(scopeSessionAttributesToViews) && callShowCreateWoodProject()")
    public void callMethod(ScopeSessionAttributesToViews scopeSessionAttributesToViews){

    }

    @Around("@annotation(scopeSessionAttributesToViews) && callShowCreateWoodProject()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint, ScopeSessionAttributesToViews scopeSessionAttributesToViews) throws Throwable {

        if(isModelInSessionAttributes(scopeSessionAttributesToViews.sessionAttribute())
                && !isLastViewInAllowedViews(scopeSessionAttributesToViews.allowedViewNames())){

            SessionStatus status = getSessionStatusParameterFromMethod(proceedingJoinPoint, scopeSessionAttributesToViews);

            if (status != null) {
                status.setComplete();

                Object returnValue = proceedingJoinPoint.proceed();

                if (returnValue instanceof String) {
                    return getRedirectToLastView();
                } else if (returnValue instanceof ModelAndView) {
                    ((ModelAndView) returnValue).setViewName(getRedirectToLastView());
                    return returnValue;
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }

    private String getRedirectToLastView(){
        return sessionNavigation.getLastView().startsWith("redirect:") ? sessionNavigation.getLastView() : "redirect:" + sessionNavigation.getLastView();
    }

    private boolean isModelInSessionAttributes(String[] sessionAttributes){
        return !Collections.disjoint(
                Arrays.asList(RequestContextHolder.getRequestAttributes().getAttributeNames(RequestAttributes.SCOPE_SESSION)),
                Arrays.asList(sessionAttributes)
        );
    }

    private boolean isLastViewInAllowedViews(String[] allowedViews){
        return Arrays.asList(allowedViews).contains(sessionNavigation.getLastView());
    }

    private SessionStatus getSessionStatusParameterFromMethod(ProceedingJoinPoint proceedingJoinPoint, ScopeSessionAttributesToViews scopeSessionAttributesToViews){
        Object[] args = proceedingJoinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();

        int argIndex = Arrays.asList(parameterNames).indexOf(scopeSessionAttributesToViews.sessionStatus());

        SessionStatus status = scopeSessionAttributesToViews.sessionStatusType().isAssignableFrom(args[argIndex].getClass()) ?
                ((SessionStatus) args[argIndex]) : null;

        return status;
    }


}
