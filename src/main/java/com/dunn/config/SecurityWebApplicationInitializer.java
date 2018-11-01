package com.dunn.config;

import com.dunn.config.webfilter.LoginErrorFilter;
import com.dunn.controller.user.LoginController;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {


}
