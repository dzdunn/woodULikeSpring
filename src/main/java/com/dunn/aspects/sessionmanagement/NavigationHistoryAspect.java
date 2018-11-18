package com.dunn.aspects.sessionmanagement;

import com.dunn.config.session.NavigationAction;
import com.dunn.config.session.SessionNavigation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Aspect
public class NavigationHistoryAspect {

    @Autowired
    private SessionNavigation sessionNavigation;

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void beanAnnotatedWithController() {

    }

//    @Before("beanAnnotatedWithController()")
//    public void beforeControllerMethod(JoinPoint joinPoint) {
//
//    }

    @AfterReturning(pointcut = "beanAnnotatedWithController()", returning = "returnValue")
    public void afterReturningControllerMethod(JoinPoint joinPoint, Object returnValue) {


        NavigationAction navigationAction = new NavigationAction();

        if (returnValue instanceof String) {
            navigationAction.setViewName((String) returnValue);
        }


        if (returnValue instanceof ModelAndView) {
            navigationAction.setViewName(((ModelAndView) returnValue).getViewName());
        }

        if(navigationAction.getViewName() != null) {
            sessionNavigation.addNavigationActionToNavigationHistory(navigationAction);
        }
    }
}
