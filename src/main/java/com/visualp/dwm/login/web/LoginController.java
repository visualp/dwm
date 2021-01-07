package com.visualp.dwm.login.web;


import com.visualp.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.dwm.login.web
 * 3. 작성일     : 2020. 07. 13. 오후 3:36
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @GetMapping("/login.html")
    public String login(){
        return "login/login";
    }
}
