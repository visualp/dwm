package com.visualp.dwm.dockerhub.webhooks.web;

import com.visualp.common.controller.BaseController;
import com.visualp.common.util.PageUtil;
import com.visualp.dwm.dockerhub.webhooks.service.DhWebHooKService;
import com.visualp.dwm.dockerhub.webhooks.vo.DhWebHookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.dockerhub.webhook.web
 * 3. 작성일     : 2021. 01. 04. 오후 6:03
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 *
 */
@Controller
@RequestMapping("/dockerhub")
public class DhWebHookController extends BaseController {

    @Autowired
    DhWebHooKService dhWebHooKService;

    @PostMapping(value = "/whook/{prjCode}")
    @ResponseBody
    public String weebhooks(@PathVariable("prjCode")String prjCode, @RequestBody String body){
        dhWebHooKService.insert(prjCode,body);
        return "res:success";
    }

    @GetMapping(value = "/log/list.html")
    public String list(Model model, @PageableDefault(size = 20) Pageable pageable){
        Page<DhWebHookVO> dhWebHookVOPage =  dhWebHooKService.selectAll(pageable);
        PageUtil pageUtil = new PageUtil(dhWebHookVOPage);
        model.addAttribute("pageUtil",pageUtil);
        model.addAttribute("list",dhWebHookVOPage.getContent());
        return "dockerhub/log/list";
    }
}
