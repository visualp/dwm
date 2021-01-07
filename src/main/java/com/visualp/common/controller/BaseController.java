package com.visualp.common.controller;

import com.visualp.common.security.SecuritySession;
import com.visualp.dwm.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.common.service
 * 3. 작성일     : 2018. 07. 11. 오전 9:59
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public class BaseController implements Controller {

    @Autowired
    protected HttpServletRequest request;

    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0,
                                      HttpServletResponse arg1) throws Exception {
        return null;
    }

    @ModelAttribute("mobile")
    public Boolean Mobile() {
        return DeviceUtils.getCurrentDevice(request).isMobile();
    }

    @ModelAttribute("device")
    public String device() {
        return DeviceUtils.getCurrentDevice(request).getDevicePlatform().name();
    }

    @ModelAttribute("_url")
    public String _url() {
        String _url = "";
        _url = request.getContextPath() == "/" ? "" : request.getContextPath();
        return _url;
    }

    @ModelAttribute("_user")
    public MemberVO _user() {
        return SecuritySession.getCurrentMember();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("password");
        //parameter trim 처리
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}