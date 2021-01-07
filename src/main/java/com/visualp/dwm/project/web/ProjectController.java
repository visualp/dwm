package com.visualp.dwm.project.web;

import com.visualp.common.controller.BaseController;
import com.visualp.common.util.PageUtil;
import com.visualp.common.util.SaveRequest;
import com.visualp.common.vo.RedirectVO;
import com.visualp.dwm.deploy.repository.DeployRepo;
import com.visualp.dwm.project.service.ProjectService;
import com.visualp.dwm.project.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.project.web
 * 3. 작성일     : 2021. 01. 04. 오후 3:15
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
    @Autowired
    ProjectService projectService;
    @Autowired
    DeployRepo deployRepo;

    @SaveRequest
    @GetMapping("/list.html")
    public String list(Model model, @PageableDefault(size = 5)Pageable pageable){
        Page<ProjectVO> projectVOPage =  projectService.selectAll_page(pageable);
        PageUtil pageUtil = new PageUtil(projectVOPage);
        model.addAttribute("pageUtil",pageUtil);
        model.addAttribute("list", projectVOPage.getContent());
        return "project/list";
    }

    @GetMapping("/insert.html")
    public String insert(Model model){
        ProjectVO projectVO = ProjectVO.builder().build();
        projectVO.setActionUrl("./insertAction.html");
        model.addAttribute("ProjectVO",projectVO);
        return "project/insert";
    }

    @PostMapping("/insertAction.html")
    public String insertAction(ProjectVO projectVO, RedirectAttributes redirectAttributes){

        RedirectVO redirectVO = RedirectVO.builder()
                .message(RedirectVO.MSG_OK)
                .build();

        try{
            projectService.insert(projectVO);
            redirectVO.setMessage(RedirectVO.MSG_OK);
            redirectVO.setUrl("/project/update.html?prjNo=" +projectVO.getPrjNo());
        }catch (Exception e){
            redirectVO.setMessage(e.getMessage());
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("redirectVO",redirectVO);
        return RedirectVO.URL_ALERT_AND_REDIRECT;
    }

    @GetMapping("/update.html")
    public String update(Model model, Long prjNo){
        ProjectVO projectVO = projectService.selectOne(prjNo);
        projectVO.setActionUrl("./updateAction.html");
        model.addAttribute("ProjectVO",projectVO);

        return "project/insert";
    }

    @PostMapping("/updateAction.html")
    public String updateAction(ProjectVO projectVO, RedirectAttributes redirectAttributes){
        RedirectVO redirectVO = RedirectVO.builder()
                .message(RedirectVO.MSG_OK)
                .url("/project/update.html?prjNo=" +projectVO.getPrjNo() )
                .build();

        try{
            projectService.update(projectVO);
        }catch (Exception e){
            redirectVO.setMessage(e.getMessage());
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("redirectVO",redirectVO);
        return RedirectVO.URL_ALERT_AND_REDIRECT;
    }

    @PostMapping("/deleteOneAction.html")
    public String deleteAction(Long prjNo,RedirectAttributes redirectAttributes){
        RedirectVO redirectVO = RedirectVO.builder()
                .message(RedirectVO.MSG_OK)
                .url("/project/list.html")
                .build();
        try {
            projectService.deleteOne(prjNo);
        }catch (Exception e){
            redirectVO.setMessage(e.getMessage());
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("redirectVO",redirectVO);
        return RedirectVO.URL_ALERT_AND_REDIRECT;
    }

    @PostMapping("/remote_deployAction.html")
    public String remote_deployAction(Long prjNo,RedirectAttributes redirectAttributes){
        ProjectVO projectVO = projectService.selectOne(prjNo);

        RedirectVO redirectVO = RedirectVO.builder()
                .message(RedirectVO.MSG_OK)
                .url("/deploy/list.html")
                .build();

        try{
            projectService.force_deploy(prjNo);
        }catch (Exception e ){
            redirectVO.setMessage(e.getMessage());
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("redirectVO",redirectVO);
        return RedirectVO.URL_ALERT_AND_REDIRECT;
    }
}
