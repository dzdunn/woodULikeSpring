package com.dunn.config;

import com.dunn.controller.path.ViewName;
import com.dunn.controller.path.ViewNameWrapper;
import com.dunn.dao.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.swing.text.View;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userService;

    final String[] resourcePatterns = {"/static/**", "/img/**"};


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("user").password(passwordEncoder.encode("root")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("root")).roles("USER", "ADMIN");

        auth.authenticationProvider(authenticationProvider());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(ViewNameWrapper.getPublicViewUrlsArray()).permitAll()
                .antMatchers(resourcePatterns).permitAll();
        http.authorizeRequests().antMatchers(ViewName.UPDATE_PASSWORD_PROCESS).hasAuthority("CHANGE_PASSWORD_PRIVILEGE");

        http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().loginPage(ViewName.LOGIN)
                .loginProcessingUrl(ViewName.LOGIN_PROCESS)
                .defaultSuccessUrl(ViewName.HOMEPAGE).failureForwardUrl(ViewName.LOGIN_PROCESS)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher(ViewName.LOGOUT, "GET"))
                .logoutSuccessUrl(ViewName.LOGIN).deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and().csrf().and().rememberMe();

    }
}
