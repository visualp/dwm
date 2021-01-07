package com.visualp.dwm.member.repo;

import com.visualp.dwm.member.vo.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.member.repo
 * 3. 작성일     : 2021. 01. 04. 오후 12:03
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface MemberRepo extends JpaRepository<MemberVO,Long> {
    MemberVO findByMbId(String mbId);
}
