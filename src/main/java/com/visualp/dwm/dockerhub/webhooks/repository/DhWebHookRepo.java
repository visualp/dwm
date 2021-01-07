package com.visualp.dwm.dockerhub.webhooks.repository;

import com.visualp.dwm.dockerhub.webhooks.vo.DhWebHookVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.dockerhub.webhooks.repository
 * 3. 작성일     : 2021. 01. 07. 오전 10:50
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface DhWebHookRepo extends JpaRepository<DhWebHookVO,Long> {
    Page<DhWebHookVO> findAll(Pageable pageable);
}
