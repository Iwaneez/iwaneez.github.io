package com.iwaneez.stuffer.config.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

/* If you want to use this security config refer it in main config */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";
    private static final String FAILED_LOGIN_URL = "/login?error";
    private static final String SUCCESSFUL_LOGOUT_URL = "/login?logout";
    private static final String ACCESS_DENIED_URL = "/403";

    private static final String FORM_PARAMETER_USERNAME = "username";
    private static final String FORM_PARAMETER_PASSWORD = "password";

    private static final String COOKIE_JSESSIONID = "JSESSIONID";

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username = ?")
                .passwordEncoder(new StandardPasswordEncoder());

        // The authentication provider below is the simplest provider you can use
        // The users, their passwords and roles are all added as clear text
//      auth
//          .inMemoryAuthentication()
//          .withUser( "admin" )
//              .password( "admin" )
//              .roles( "ADMIN" )
//              .and()
//          .withUser( "user" )
//              .password( "user" )
//              .roles( "USER" );


        // The authentication provider below hashes incoming passwords using SHA1
        // The users passwords below are hashed using SHA1 (see README for values)
//      auth
//          .inMemoryAuthentication()
//          .passwordEncoder( new ShaPasswordEncoder() )
//          .withUser( "admin" )
//              .password( "d033e22ae348aeb5660fc2140aec35850c4da997" )
//              .roles( "ADMIN" )
//              .and()
//          .withUser( "user" )
//              .password( "12dea96fec20593566ab75692c9949596833adc9" )
//              .roles( "USER" );


        // The authentication provider below uses JDBC to retrieve your credentials
        // The data source bean configuration can be found at the bottom of this file
        // The first example uses the default Spring Security tables, see link below
        // http://docs.spring.io/spring-security/site/docs/3.0.x/reference/appendix-schema.html
//      auth
//          .jdbcAuthentication()
//          .dataSource( dataSource )
//          .passwordEncoder( new ShaPasswordEncoder() );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**","/resources/**").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                //.antMatchers("/**").access("isAuthenticated()")
                .and()
                .formLogin().permitAll()
                .loginPage(LOGIN_URL)
                .failureUrl(FAILED_LOGIN_URL)
                .usernameParameter(FORM_PARAMETER_USERNAME)
                .passwordParameter(FORM_PARAMETER_PASSWORD)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl(SUCCESSFUL_LOGOUT_URL)
                .deleteCookies(COOKIE_JSESSIONID)
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .accessDeniedPage(ACCESS_DENIED_URL)
                .and()
                .csrf().disable();
    }
}
