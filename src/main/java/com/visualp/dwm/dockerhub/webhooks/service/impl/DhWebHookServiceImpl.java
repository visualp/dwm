package com.visualp.dwm.dockerhub.webhooks.service.impl;

import com.visualp.common.security.SecuritySession;
import com.visualp.dwm.deploy.service.DeployService;
import com.visualp.dwm.deploy.val.DeployVAL;
import com.visualp.dwm.deploy.vo.DeployVO;
import com.visualp.dwm.dockerhub.webhooks.repository.DhWebHookRepo;
import com.visualp.dwm.dockerhub.webhooks.service.DhWebHooKService;
import com.visualp.dwm.dockerhub.webhooks.vo.DhWebHookVO;
import com.visualp.dwm.project.service.ProjectService;
import com.visualp.dwm.project.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.dockerhub.webhooks.service.impl
 * 3. 작성일     : 2021. 01. 05. 오전 10:29
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Service
@Transactional
public class DhWebHookServiceImpl implements DhWebHooKService {
    @Autowired
    ProjectService projectService;

    @Autowired
    DhWebHookRepo dhWebHookRepo;

    @Override
    public void insert(String prjCode, String json_body) {
        ProjectVO projectVO = projectService.selectOne(prjCode);
        if(projectVO != null){

            DhWebHookVO dhWebHookVO = DhWebHookVO.builder()
                    .dwJsonData(json_body)
                    .build();
            projectVO.add_dhWebHoosVO(dhWebHookVO);

            DeployVO deployVO = DeployVO.builder()
                    .prjNo(projectVO.getPrjNo())
                    .prjCmd(projectVO.getPrjCmd())
                    .dpStatus(DeployVAL.DP_STATUS_대기)
                    .regMbId("admin")
                    .regMbName("docker")
                    .build();
            projectVO.add_deployVO(deployVO);

        }
    }

    @Override
    public Page<DhWebHookVO> selectAll(Pageable pageable) {
        Page<DhWebHookVO> dhWebHookVOPage = dhWebHookRepo.findAll(pageable);

        return dhWebHookVOPage;
    }
}
