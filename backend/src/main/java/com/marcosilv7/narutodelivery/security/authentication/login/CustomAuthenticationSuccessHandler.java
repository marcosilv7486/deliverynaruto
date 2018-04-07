package com.marcosilv7.narutodelivery.security.authentication.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.security.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;
    private final TokenUtil tokenUtil;

    @Autowired
    public CustomAuthenticationSuccessHandler(ObjectMapper mapper, TokenUtil tokenUtil) {
        this.mapper = mapper;
        this.tokenUtil = tokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        String accessToken = tokenUtil.createAccessJwtToken(userContext);
        String refreshToken = tokenUtil.createAccessJwtToken(userContext);
        Map<String, Object> tokenMap = new HashMap<>();
        Map<String,String> data = new HashMap<>();
        data.put("token", accessToken);
        data.put("refreshToken", refreshToken);
        data.put("email", userContext.getUserName());
        data.put("fullname", userContext.getFullName());
        data.put("avatar", userContext.getAvatar());
        data.put("userId", userContext.getUserId().toString());
        tokenMap.put("data", data);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(httpServletResponse.getWriter(), tokenMap);

        clearAuthenticationAttributes(httpServletRequest);
    }

    private  void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
