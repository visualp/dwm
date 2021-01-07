package com.visualp.common.security.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.common.security.impl
 * 3. 작성일     : 2020. 07. 14. 오후 12:26
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

@Service(value = "customLogoutSuccessHandler")
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    /**
     * 로그아웃 성공시 Login 페이지로 redirect 처리
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String URL = httpServletRequest.getContextPath() + "/login/login.html";
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.sendRedirect(URL);
    }

}
