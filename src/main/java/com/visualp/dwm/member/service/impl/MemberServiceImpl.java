package com.visualp.dwm.member.service.impl;

import com.visualp.dwm.member.repo.MemberRepo;
import com.visualp.dwm.member.service.MemberService;
import com.visualp.dwm.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.member.service.impl
 * 3. 작성일     : 2021. 01. 04. 오후 12:02
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepo memberRepo;

    @Override
    public MemberVO selectOne(String mbId) {
        return memberRepo.findByMbId(mbId);
    }

    @Override
    public MemberVO selectOne(Long mbNo) {
        return memberRepo.findById(mbNo).orElse(null);
    }
}
