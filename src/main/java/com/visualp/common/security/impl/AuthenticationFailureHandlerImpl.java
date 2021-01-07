package com.visualp.common.security.impl;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.common.security.impl
 * 3. 작성일     : 2020. 07. 14. 오후 12:24
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

@Service("customAuthenticationFailureHandler")
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/login.html");
    }
}