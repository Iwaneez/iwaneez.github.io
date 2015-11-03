package com.iwaneez.stuffer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* If you want to use this security config refer it in main config */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static final String LOGIN_URL = "/login";
    private static final String FAILED_LOGIN_URL = "/login?error";
    private static final String SUCCESFULL_LOGOUT_URL = "/login?logout";
    private static final String ACCESS_DENIED_URL = "/403";
    
    private static final String FRORM_PARAMATER_USERNAME = "username";
    private static final String FORM_PARAMATER_PASSWORD = "password";
    
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username = ?")
            .authoritiesByUsernameQuery("select username, role from user_roles where username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**").access("isAuthenticated()")
            //.antMatchers("/**").access("isAuthenticated()")
            .and()
                .formLogin().loginPage(LOGIN_URL)
                .failureUrl(FAILED_LOGIN_URL)
                .usernameParameter(FRORM_PARAMATER_USERNAME)
                .passwordParameter(FORM_PARAMATER_PASSWORD)
            .and()
                .logout().logoutSuccessUrl(SUCCESFULL_LOGOUT_URL)
            .and()
                .exceptionHandling()
                .accessDeniedPage(ACCESS_DENIED_URL)
            .and()
                .csrf();
    }
}
