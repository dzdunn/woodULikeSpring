package com.dunn.config.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@SessionScope
public class SessionNavigation {

    private static List<NavigationAction> navigationHistory = new ArrayList<>();

    public void addNavigationActionToNavigationHistory(NavigationAction navigationAction){
        navigationHistory.add(navigationAction);
    }

    public List<NavigationAction> getNavigationHistory(){
        return Collections.unmodifiableList(navigationHistory);
    }

    public String getLastView(){
        return navigationHistory.get(0) != null ? navigationHistory.get(navigationHistory.size()-1).getViewName() : "";
    }


}
