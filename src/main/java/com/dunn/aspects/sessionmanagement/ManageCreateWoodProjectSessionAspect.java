package com.dunn.aspects.sessionmanagement;

import com.dunn.config.session.SessionNavigation;
import com.dunn.controller.path.ViewName;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Component
@Aspect
public class ManageCreateWoodProjectSessionAspect {

    @Autowired
    private SessionNavigation sessionNavigation;


    @Pointcut("@annotation(ManageCreateWoodProjectSession)")
    public void callShowCreateWoodProject() {

    }

    @Pointcut(value = "@annotation(manageCreateWoodProjectSession) && callShowCreateWoodProject()")
    public void callMethod(ManageCreateWoodProjectSession manageCreateWoodProjectSession){

    }
//
//    @Before(value = "@annotation(manageCreateWoodProjectSession) && callShowCreateWoodProject()")
//    public void beforeShowCreateWoodProject(JoinPoint joinPoint, ManageCreateWoodProjectSession manageCreateWoodProjectSession) {
//
//        if (!Arrays.asList(manageCreateWoodProjectSession.allowedViewNames()).contains(sessionNavigation.getLastView())) {
//            Object[] args = joinPoint.getArgs();
//            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//            String[] parameterNames = methodSignature.getParameterNames();
//
//            int argIndex = Arrays.asList(parameterNames).indexOf(manageCreateWoodProjectSession.sessionStatus());
//
//            SessionStatus status = manageCreateWoodProjectSession.sessionStatusType().isAssignableFrom(args[argIndex].getClass()) ?
//                    ((SessionStatus) args[argIndex]) : null;
//            if (status != null) {
//                if (RequestContextHolder.getRequestAttributes().getAttribute("woodProjectDTO", RequestAttributes.SCOPE_SESSION) != null) {
//                    RequestContextHolder.getRequestAttributes().removeAttribute("woodProjectDTO", RequestAttributes.SCOPE_REQUEST);
//                    RequestContextHolder.getRequestAttributes().removeAttribute("woodProjectDTO", RequestAttributes.SCOPE_SESSION);
//                    status.setComplete();
//
//                }
//
//            } else {
//                throw new ClassCastException("Argument index could not be cast to SessionStatus type.");
//            }
//            ;
//        }


//
//        if (!sessionNavigation.getLastView().equals("redirect:" + ViewName.CREATE_WOOD_PROJECT)){
//            RequestContextHolder.currentRequestAttributes().removeAttribute("woodProjectDTO", RequestAttributes.SCOPE_SESSION);

//        }


//    }
//
//    @AfterReturning(value = "callShowCreateWoodProject()", returning = "returnValue")
//    public void afterShowCreateWoodProject(JoinPoint joinPoint, Object returnValue) {
//
//        if (returnValue instanceof String) {
//
//        }
//    }

    @Around("@annotation(manageCreateWoodProjectSession) && callShowCreateWoodProject()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint, ManageCreateWoodProjectSession manageCreateWoodProjectSession) throws Throwable {

        if(RequestContextHolder.getRequestAttributes().getAttribute("woodProjectDTO", RequestAttributes.SCOPE_SESSION) != null && !sessionNavigation.getLastView().equals("redirect:" + ViewName.CREATE_WOOD_PROJECT)){
            Object[] args = proceedingJoinPoint.getArgs();
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            String[] parameterNames = methodSignature.getParameterNames();
            int argIndex = Arrays.asList(parameterNames).indexOf(manageCreateWoodProjectSession.sessionStatus());

            SessionStatus status = manageCreateWoodProjectSession.sessionStatusType().isAssignableFrom(args[argIndex].getClass()) ?
                    ((SessionStatus) args[argIndex]) : null;
            if (status != null) {
                status.setComplete();

                String lastView = sessionNavigation.getLastView().startsWith("redirect:") ? sessionNavigation.getLastView() : "redirect:" + sessionNavigation.getLastView();

                Object returnValue = proceedingJoinPoint.proceed();

                if (returnValue instanceof String) {
                    return returnValue;
                } else if (returnValue instanceof ModelAndView) {
                    ((ModelAndView) returnValue).setViewName(lastView);
                    return returnValue;
                }
            }
        }


        return proceedingJoinPoint.proceed();
    }


}
