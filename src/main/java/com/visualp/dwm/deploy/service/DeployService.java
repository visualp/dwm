package com.visualp.dwm.deploy.service;

import com.visualp.dwm.deploy.vo.DeployVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.deploy.service
 * 3. 작성일     : 2021. 01. 05. 오후 2:33
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface DeployService {
    void insert(DeployVO deployVO);
    void update(DeployVO deployVO);
    void deleteOne(Long dpNo);
    DeployVO selectOne(Long dpNo);

    /**
     * schedule용 status-> 대기 구하기 top 1 order by dpNo asc
     * @return
     */
    DeployVO selectOne_proc_first();
    /**
     * 해당 프로젝트의 기록
     * @param pageable
     * @param prjNo
     * @return
     */
    Page<DeployVO> selectAll(Pageable pageable , Long prjNo);

    /**
     * 전체기록
     * @param pageable
     * @return
     */
    Page<DeployVO> selectAll(Pageable pageable);

    /**
     * deploy scheduler
     */
    void schedule_deploy(Long dpNo);
}
