package com.dunn.config.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Stack;

@Component
@SessionScope
public class SessionNavigation {

    //The URIs requested
    private static final Stack<NavigationAction> navigationRequestHistory = new Stack<>();

    //The response i.e. the page or redirect or forward URI
    private static final Stack<NavigationAction> navigationResponseHistory = new Stack<>();

    public void addNavigationRequest(NavigationAction navigationAction){
        navigationRequestHistory.push(navigationAction);
    }

    public void addNavigationResponse(NavigationAction visitedPage){
        navigationResponseHistory.push(visitedPage);
    }

    public Stack<NavigationAction> getNavigationHistory(){
        return navigationRequestHistory;
    }

    public Stack<NavigationAction> getNavigationResponseHistory(){
        return navigationResponseHistory;
    }

    public String getLastRequest(){
        return navigationRequestHistory.peek()!= null ? navigationRequestHistory.peek().getViewName() : "";
    }

    public String getLastResponse(){
        return navigationResponseHistory.peek() != null ? navigationRequestHistory.peek().getViewName() : "";
    }




}
