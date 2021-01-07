package com.visualp.dwm.project.repo;

import com.visualp.dwm.project.vo.ProjectVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.project.repo
 * 3. 작성일     : 2021. 01. 04. 오후 3:08
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface ProjectRepo extends JpaRepository<ProjectVO,Long > {
    Page<ProjectVO> findAllByOrderByPrjFlagDescPrjNoDesc(Pageable pageable);
    ProjectVO findByPrjCode(String prjCode);
}
