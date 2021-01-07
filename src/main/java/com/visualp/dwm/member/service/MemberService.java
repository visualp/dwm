package com.visualp.dwm.member.service;

import com.visualp.dwm.member.vo.MemberVO;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.member.service
 * 3. 작성일     : 2021. 01. 04. 오후 12:02
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public interface MemberService {
    MemberVO selectOne(String mbId);
    MemberVO selectOne(Long mbNo);
}
