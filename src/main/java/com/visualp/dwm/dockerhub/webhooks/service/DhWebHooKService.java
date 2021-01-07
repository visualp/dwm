package com.visualp.dwm.dockerhub.webhooks.service;

import com.visualp.dwm.dockerhub.webhooks.vo.DhWebHookVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.dockerhub.webhooks.service
 * 3. 작성일     : 2021. 01. 05. 오전 10:28
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface DhWebHooKService {
    void insert(String prjCode, String json_body);

    /**
     * 로그 모두 조회
     * @param pageable
     * @return
     */
    Page<DhWebHookVO> selectAll(Pageable pageable);
}
