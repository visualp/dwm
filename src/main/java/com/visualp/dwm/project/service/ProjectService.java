package com.visualp.dwm.project.service;

import com.visualp.dwm.project.vo.ProjectVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.project.service
 * 3. 작성일     : 2021. 01. 04. 오후 3:06
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface ProjectService {
    ProjectVO selectOne(Long pNo);
    ProjectVO selectOne(String prjCode);
    Page<ProjectVO> selectAll_page(Pageable pageable);
    void insert(ProjectVO projectVO);
    void update(ProjectVO projectVO);
    void deleteOne(Long prjNo);

    /**
     * 강제배포
     * @param prjNo
     */
    void force_deploy(Long prjNo);
}
