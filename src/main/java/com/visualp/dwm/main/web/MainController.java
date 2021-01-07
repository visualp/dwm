package com.visualp.dwm.main.web;

import com.visualp.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.main.web
 * 3. 작성일     : 2021. 01. 04. 오후 1:46
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {
    @GetMapping(value = {"/","/index.html"})
    public String main(Model model){
        return "main/index";
    }

}
