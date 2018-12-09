package com.dunn.config.security.filter.httpwrappers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.Collection;

public class PostToGetRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    private HttpSession session;

    private Collection<Part> parts;

    public PostToGetRequestWrapper(HttpServletRequest request) {
        super(request);
        session = getSession();
    }

    @Override
    public Collection<Part> getParts(){
        return parts;
    }

    public void setParts(Collection<Part> parts){
        this.parts = parts;
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
