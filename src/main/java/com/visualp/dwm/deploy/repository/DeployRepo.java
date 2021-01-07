package com.visualp.dwm.deploy.repository;

import com.visualp.dwm.deploy.vo.DeployVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.deploy.repository
 * 3. 작성일     : 2021. 01. 05. 오후 2:34
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface DeployRepo extends JpaRepository<DeployVO,Long> {
   /**
    * 스케줄 처리용
    * @param dpStatus
    * @return
    */
   DeployVO findFirstByDpStatusOrderByRegDatetimeAsc(String dpStatus);

   /**
    * @param pageable
    * @param prjNo
    * @return
    */
   Page<DeployVO> findAllByPrjNoOrderByDpNoAsc(Pageable pageable, Long prjNo);

   /**
    * 전체조회
    * @param pageable
    * @return
    */
   Page<DeployVO> findAllByOrderByDpNoDesc(Pageable pageable);
}
