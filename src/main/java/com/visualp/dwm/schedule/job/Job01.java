package com.visualp.dwm.schedule.job;

import com.visualp.dwm.deploy.service.DeployService;
import com.visualp.dwm.deploy.val.DeployVAL;
import com.visualp.dwm.deploy.vo.DeployVO;
import com.visualp.dwm.schedule.queue.util.SqueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.schedule.job
 * 3. 작성일     : 2021. 01. 06. 오전 9:56
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Component
@Transactional
public class Job01 {

    @Autowired
    DeployService deployService;

    /**
     * 10초 -> 한 번씩 실행
     */
    @Scheduled(fixedRate = 1000*10)
    public void job_allocate(){
        DeployVO deployVO = deployService.selectOne_proc_first();
        if(deployVO != null) {
            deployVO.setDpStatus(DeployVAL.DP_STATUS_진행);
            SqueueUtil.addNo(deployVO.getDpNo());
        }
    }

    @Scheduled(fixedRate = 1000*10)
    public void job_deploy(){
        if( SqueueUtil.doing() == false){
            //deploy실행
            Long dpNo = SqueueUtil.getNo();
            if(dpNo!=null) {
                deployService.schedule_deploy(dpNo);
                SqueueUtil.init_currentJobNo();
            }
        }else{
            System.out.println("res:doing!");
        }
    }

}
