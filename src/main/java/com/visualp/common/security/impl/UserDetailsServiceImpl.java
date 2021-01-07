package com.visualp.common.security.impl;

import com.visualp.dwm.member.repo.MemberRepo;
import com.visualp.dwm.member.service.MemberService;
import com.visualp.dwm.member.vo.MemberRoleVO;
import com.visualp.dwm.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.common.security.impl
 * 3. 작성일     : 2020. 07. 14. 오전 11:49
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Service("customUserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = null;
        MemberVO memberVO = memberService.selectOne(s);

        if (memberVO != null) {
            MemberRoleVO memberRoleVO = MemberRoleVO.builder().roId("ROLE_ADMIN").roName("관리자").build();
            memberVO.getMemberRoleVOS().add(memberRoleVO);
            authorities = buildUserAuthority(memberVO.getMemberRoleVOS());
        } else {
            throw new BadCredentialsException("사용자가 존재하지 않습니다.");
        }

        return new UserDetailsImpl(memberVO, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<MemberRoleVO> memberRoleVOS) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (MemberRoleVO memberRoleVO : memberRoleVOS) {
            authorities.add(new SimpleGrantedAuthority(memberRoleVO.getRoId()));
        }
        return authorities;
    }
}
