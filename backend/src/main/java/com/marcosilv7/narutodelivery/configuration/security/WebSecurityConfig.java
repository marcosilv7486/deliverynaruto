package com.marcosilv7.narutodelivery.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.security.authentication.jwt.JwtAuthenticationProvider;
import com.marcosilv7.narutodelivery.security.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import com.marcosilv7.narutodelivery.security.authentication.jwt.SkipPathRequestMatcher;
import com.marcosilv7.narutodelivery.security.authentication.login.CustomAuthenticationProvider;
import com.marcosilv7.narutodelivery.security.authentication.login.CustomLoginProcessingFilter;
import com.marcosilv7.narutodelivery.security.entrypoint.RestAuthenticationEntryPoint;
import com.marcosilv7.narutodelivery.security.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String  JWT_TOKEN_HEADER_PARAM="X-Authorization";
    public static final String CONTENT_TYPE="application/json";

    @Autowired
    private  AuthenticationSuccessHandler successHandler;
    @Autowired
    private  AuthenticationFailureHandler failureHandler;
    @Autowired
    private  CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private  JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private  TokenUtil tokenUtil;
    @Autowired
    private  RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private CustomLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        CustomLoginProcessingFilter filter = new CustomLoginProcessingFilter(
                Api.LOGIN_PATH, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(Api.REFRESH_TOKEN_PATH,Api.USER_REGISTRATION_PATH,Api.SWAGGER_PATH,
                Api.SWAGGER_RESOURCES_PATH);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, Api.ROOT_PATH+"/**");
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, matcher, tokenUtil);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.restAuthenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(Api.LOGIN_PATH).permitAll()
                .antMatchers(Api.SWAGGER_PATH).permitAll()
                .antMatchers(Api.SWAGGER_RESOURCES_PATH).permitAll()
                .antMatchers(Api.REFRESH_TOKEN_PATH).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(Api.ROOT_PATH+"/**").authenticated()
                .and()
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
