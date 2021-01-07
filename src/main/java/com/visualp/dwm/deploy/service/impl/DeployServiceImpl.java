package com.visualp.dwm.deploy.service.impl;

import com.jcraft.jsch.*;
import com.visualp.common.slack.service.SlackService;
import com.visualp.common.util.CustomBeanUtils;
import com.visualp.dwm.deploy.repository.DeployRepo;
import com.visualp.dwm.deploy.service.DeployService;
import com.visualp.dwm.deploy.val.DeployVAL;
import com.visualp.dwm.deploy.vo.DeployVO;
import com.visualp.dwm.project.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.deploy.service.impl
 * 3. 작성일     : 2021. 01. 05. 오후 2:33
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Service
@Transactional
public class DeployServiceImpl implements DeployService {

    @Value("${dwm.doamin}")
    private String domain;

    @Autowired
    private DeployRepo deployRepo;

    @Autowired
    private SlackService slackService;

    @Override
    public void insert(DeployVO deployVO) {
        deployRepo.save(deployVO);
    }

    @Override
    public void update(DeployVO deployVO) {
        DeployVO deployVO01 = this.selectOne(deployVO.getDpNo());
        if(deployVO01 != null){
            CustomBeanUtils.copyProperties(deployVO, deployVO01);
        }
    }

    @Override
    public void deleteOne(Long dpNo) {
        deployRepo.deleteById(dpNo);
    }

    @Override
    public DeployVO selectOne(Long dpNo) {
        DeployVO deployVO =  deployRepo.findById(dpNo).orElse(null);
        if(deployVO != null){
            this.config_logLink(deployVO);
        }
        return deployVO;
    }

    @Override
    public DeployVO selectOne_proc_first() {
        return deployRepo.findFirstByDpStatusOrderByRegDatetimeAsc("대기");
    }

    @Override
    public Page<DeployVO> selectAll(Pageable pageable, Long prjNo) {
        Page<DeployVO> deployVOPage =  deployRepo.findAllByPrjNoOrderByDpNoAsc(pageable,prjNo);
        for(DeployVO deployVO : deployVOPage.getContent()){
            this.config_logLink(deployVO);
        }
        return deployVOPage;
    }

    @Override
    public Page<DeployVO> selectAll(Pageable pageable) {
        Page<DeployVO> deployVOPage =  deployRepo.findAllByOrderByDpNoDesc(pageable);
        for(DeployVO deployVO : deployVOPage.getContent()){
            this.config_logLink(deployVO);
        }
        return deployVOPage;
    }

    @Override
    public void schedule_deploy(Long dpNo) {
        DeployVO deployVO = this.selectOne(dpNo);
        ProjectVO projectVO = deployVO.getProjectVO();

        if (projectVO != null) {
            JSch jsch = new JSch();

            try {

                Session session = jsch.getSession(projectVO.getPrjRid(), projectVO.getPrjRaddress(), 22);
                session.setPassword(projectVO.getPrjRpassword());
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();  //연결 시도

                Channel channel = session.openChannel("exec");  //채널접속
                ChannelExec channelExec = (ChannelExec) channel; //명령 전송 채널사용
                channelExec.setPty(true);
                channelExec.setCommand(deployVO.getPrjCmd()); //내가 실행시킬 명령어를 입력

                //콜백을 받을 준비.
                StringBuilder outputBuffer = new StringBuilder();
                InputStream in = channel.getInputStream();
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();  //실행
                byte[] tmp = new byte[1024];
                while (true) {

                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        System.out.println(new String(tmp));
                        outputBuffer.append(new String(tmp, 0, i));
                        if (i < 0) break;
                    }

                    if (channel.isClosed()) {
                        deployVO.setDpLog(outputBuffer.toString());
                        if (deployVO.getDpLog().contains("deploy:success")) {
                            deployVO.setDpSflag(1);
                        } else {
                            deployVO.setDpSflag(0);
                        }
                        deployVO.setResDatetime(new Date());

                        channel.disconnect();
                        break;
                    }
                }//end while
            } catch (JSchException e) {
                deployVO.setDpSflag(0);
                deployVO.setDpLog(e.getStackTrace().toString());
                e.printStackTrace();
            } catch (IOException e) {
                deployVO.setDpSflag(0);
                deployVO.setDpLog(e.getStackTrace().toString());
                e.printStackTrace();
            }
        }

        deployVO.setDpStatus(DeployVAL.DP_STATUS_완료);
        String message = "";
        message+="jobNo:" + deployVO.getDpNo() + "\n";
        message+="project:" + deployVO.getProjectVO().getPrjName() + "\n";
        message+="deploy:" +  (deployVO.getDpSflag().equals(1) ? "success" : "failed") + "\n";
        message+="log:" + "<" + deployVO.getLogLink() + ">";
        slackService.sendMessage(message);

    }//end method

    private void config_logLink(DeployVO deployVO){
        String logLink="";
        logLink =  this.domain;
        logLink += "/deploy/log.html?dpNo=" + deployVO.getDpNo();
        deployVO.setLogLink(logLink);
    }
}
