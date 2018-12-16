package com.dunn.aspects.sessionmanagement;

import com.dunn.config.session.NavigationAction;
import com.dunn.config.session.SessionNavigation;
import com.dunn.model.user.WoodulikeUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Aspect
public class NavigationHistoryAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(NavigationHistoryAspect.class);

    @Autowired
    private SessionNavigation sessionNavigation;

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void beanAnnotatedWithController() {

    }

    @Before("beanAnnotatedWithController()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        NavigationAction navigationAction = new NavigationAction();
        navigationAction.setViewName(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI());

        LOGGER.info("USER: " + resolveUsename() + ", REQUEST: " + navigationAction.getViewName());
        sessionNavigation.addNavigationRequest(navigationAction);
    }

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
            sessionNavigation.addNavigationResponse(navigationAction);

            LOGGER.info("USER: " + resolveUsename() + ", RESPONSE: " + navigationAction.getViewName());
        }
    }

    private String resolveUsename(){
        if(isUser()){
            WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return woodulikeUser.getUsername();
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private boolean isUser(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().map(x -> ((GrantedAuthority) x).getAuthority()).anyMatch(x -> x.equals("ROLE_USER"));
    }
}
