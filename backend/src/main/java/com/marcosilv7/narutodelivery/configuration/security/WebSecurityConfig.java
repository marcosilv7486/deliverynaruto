package com.marcosilv7.narutodelivery.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private  AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final TokenUtil tokenUtil;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public static final String ROOT_PATH="/api/v1";
    public static final String LOGIN_PATH=ROOT_PATH+"/security/authenticacion";
    public static final String REFRESH_TOKEN_PATH=ROOT_PATH+"/security/refreshtoken";
    public static final String USER_REGISTRATION_PATH=ROOT_PATH+"/users/registration";
    public static final String SWAGGER_PATH="/swagger-resources/configuration/ui/**";
    public static final String SWAGGER_RESOURCES_PATH="/configuration/ui/**";

    @Autowired
    public WebSecurityConfig(
                             AuthenticationSuccessHandler successHandler,
                             AuthenticationFailureHandler failureHandler,
                             CustomAuthenticationProvider customAuthenticationProvider,
                             ObjectMapper objectMapper,
                             TokenUtil tokenUtil,
                             RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                             JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.objectMapper = objectMapper;
        this.tokenUtil = tokenUtil;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }


    private CustomLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        CustomLoginProcessingFilter filter = new CustomLoginProcessingFilter(
                LOGIN_PATH, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(REFRESH_TOKEN_PATH, USER_REGISTRATION_PATH,SWAGGER_PATH,
                SWAGGER_RESOURCES_PATH);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, USER_REGISTRATION_PATH+"/**");
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
                .antMatchers(LOGIN_PATH).permitAll()
                .antMatchers(SWAGGER_PATH).permitAll()
                .antMatchers(SWAGGER_RESOURCES_PATH).permitAll()
                .antMatchers(REFRESH_TOKEN_PATH).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(ROOT_PATH).authenticated()
                .and()
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
