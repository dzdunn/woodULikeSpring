package com.dunn.config.session;


import org.springframework.http.HttpRequest;

public class NavigationAction {

    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
