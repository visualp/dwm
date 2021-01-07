package com.visualp.common.security.impl;

import com.visualp.dwm.member.vo.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.common.security.impl
 * 3. 작성일     : 2020. 07. 14. 오전 11:41
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public class UserDetailsImpl implements UserDetails {

    //intranet 직원정보
    private final MemberVO memberVO;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(MemberVO memberVO,List<GrantedAuthority> authorities) {
        this.memberVO = memberVO;
        this.authorities = authorities;
    }

    public MemberVO getMemberVO() {
        return memberVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return memberVO.getMbPasswd();
    }

    @Override
    public String getUsername() {
        return memberVO.getMbName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(memberVO.getMbFlag().equals(1)) {
            return true;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return memberVO.getMbFlag().equals(1);
    }

    public boolean equals(Object o) {
        if (o instanceof UserDetailsImpl) {
            return memberVO.getMbId().equals(((UserDetailsImpl) o).getUsername());
        }
        return false;
    }

    /**
     * session 로그인 중복체크 반드시 hashCode 매소드가 존재해야 구현됨
     *
     * @return
     */
    public int hashCode() {
        return memberVO.getMbId().hashCode();
    }

}
