package com.visualp.dwm.project.service.impl;

import com.visualp.common.security.SecuritySession;
import com.visualp.common.util.CustomBeanUtils;
import com.visualp.dwm.deploy.vo.DeployVO;
import com.visualp.dwm.project.repo.ProjectRepo;
import com.visualp.dwm.project.service.ProjectService;
import com.visualp.dwm.project.vo.ProjectVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.project.service.impl
 * 3. 작성일     : 2021. 01. 04. 오후 3:07
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepo projectRepo;

    @Value("${dwm.doamin}")
    private String domain;

    @Override
    public ProjectVO selectOne(Long pNo) {
        ProjectVO projectVO = projectRepo.findById(pNo).orElse(null);
        if(projectVO != null){
            config_whookAddress(projectVO);
        }
        return projectVO;
    }

    @Override
    public ProjectVO selectOne(String prjCode) {
        ProjectVO projectVO = projectRepo.findByPrjCode(prjCode);
        if(projectVO != null){
            config_whookAddress(projectVO);
        }
        return projectVO;
    }

    @Override
    public Page<ProjectVO> selectAll_page(Pageable pageable) {
        Page<ProjectVO> projectVOPage = projectRepo.findAllByOrderByPrjFlagDescPrjNoDesc(pageable);
        for(ProjectVO projectVO : projectVOPage.getContent()){
            config_whookAddress(projectVO);
        }
        return projectVOPage;
    }

    @Override
    public void insert(ProjectVO projectVO) {
        if(StringUtils.isEmpty(projectVO.getPrjCode())){
            String prjCode = UUID.randomUUID().toString().replaceAll("-","");
            projectVO.setPrjCode(prjCode);
        }
        projectVO.setRegMbId(SecuritySession.getCurrentMember().getMbId());
        projectVO.setRegMbName(SecuritySession.getCurrentMember().getMbName());
        projectRepo.save(projectVO);
    }

    @Override
    public void update(ProjectVO projectVO) {
        projectVO.setUpDatetime(new Date());
        ProjectVO projectVO1 = this.selectOne(projectVO.getPrjNo());
        if(projectVO1 != null){
            CustomBeanUtils.copyProperties(projectVO,projectVO1);
        }
    }

    @Override
    public void deleteOne(Long prjNo) {
        projectRepo.deleteById(prjNo);
    }

    @Override
    public void force_deploy(Long prjNo) {
        ProjectVO projectVO = this.selectOne(prjNo);
        if(projectVO != null){
            DeployVO deployVO = DeployVO.builder()
                    .dpStatus("대기")
                    .prjCmd(projectVO.getPrjCmd())
                    .regMbId(SecuritySession.getCurrentMember().getMbId())
                    .regMbName(SecuritySession.getCurrentMember().getMbName())
                    .build();
            projectVO.add_deployVO(deployVO);
        }
    }

    public void config_whookAddress(ProjectVO projectVO){
        String whookAddress="";
        whookAddress = this.domain;
        whookAddress += "/dockerhub/whook/" + projectVO.getPrjCode();
        projectVO.setWhookAddress(whookAddress);
    }

}
