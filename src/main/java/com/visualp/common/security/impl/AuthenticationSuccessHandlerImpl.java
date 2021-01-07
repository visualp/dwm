package com.visualp.common.security.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.common.security.impl
 * 3. 작성일     : 2020. 07. 14. 오후 12:06
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    String targetUrlParameter;
    String defaultUrl = "/";
    boolean useReferer;
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = null;

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        targetUrl = request.getParameter("targetUrl") == null ? "" : request.getParameter("targetUrl");
        String _domain = request.getScheme() + "://" + request.getServerName() + "/";

        if (savedRequest == null) {
            savedRequest = new SimpleSavedRequest();
        }
        int intRedirectStrategy = decideRedirectStrategy(request, response);

        switch (intRedirectStrategy) {
            case 1:
                redirectStrategy.sendRedirect(request, response, targetUrl);
                break;
            case 2:
                redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
                break;
            case 3:
                redirectStrategy.sendRedirect(request, response, request.getHeader("REFERER"));
                break;
            default:
                redirectStrategy.sendRedirect(request, response, defaultUrl);
        }

    }


    /**
     *      * 인증 성공후 어떤 URL로 redirect 할지를 결정한다
     *      * 판단 기준은 targetUrlParameter 값을 읽은 URL이 존재할 경우 그것을 1순위
     *      * 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을 2순위
     *      * 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을 3순위
     *      * 3순위 URL이 없을 경우 Default URL을 4순위로 한다
     *      * @param request
     *      * @param response
     *      * @return   1 : targetUrlParameter 값을 읽은 URL
     *      *            2 : Session에 저장되어 있는 URL
     *      *            3 : referer 헤더에 있는 url
     *      *            0 : default url
     *      
     */
    private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
        int result = 0;
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (!"".equals(targetUrlParameter)) {
            String targetUrl = request.getParameter("targetUrl");
            //System.out.println("inner:" + targetUrl);
            if (targetUrl != null) {
                result = 1;
            } else {
                if (savedRequest != null) {
                    result = 2;
                } else {
                    String refererUrl = request.getHeader("REFERER");
                    if (useReferer && targetUrl != null) {
                        result = 3;
                    } else {
                        result = 0;
                    }
                }
            }
            return result;
        }

        if (savedRequest != null) {
            result = 2;
            return result;
        }

        String refererUrl = request.getHeader("REFERER");
        if (useReferer && refererUrl != null) {
            result = 3;
        } else {
            result = 0;
        }

        return result;
    }

}
