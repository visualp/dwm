package com.visualp.dwm.deploy.web;

import com.visualp.common.controller.BaseController;
import com.visualp.common.slack.service.SlackService;
import com.visualp.common.util.PageUtil;
import com.visualp.dwm.deploy.service.DeployService;
import com.visualp.dwm.deploy.vo.DeployVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.deploy.web
 * 3. 작성일     : 2021. 01. 05. 오후 3:32
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Controller
@RequestMapping("/deploy")
public class DeployController extends BaseController {
    @Autowired
    DeployService deployService;

    @GetMapping("/list.html")
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable){
        Page<DeployVO> deployVOPage = deployService.selectAll(pageable);
        PageUtil pageUtil = new PageUtil(deployVOPage);
        model.addAttribute("list",deployVOPage.getContent());
        model.addAttribute("pageUtil",pageUtil);

        return "deploy/list";
    }

    @GetMapping("/log.html")
    public String log(Model model,Long dpNo){
        DeployVO deployVO = deployService.selectOne(dpNo);
        model.addAttribute("deployVO",deployVO);

        return "deploy/log";
    }
}
