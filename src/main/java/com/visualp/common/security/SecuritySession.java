package com.visualp.common.security;

import com.visualp.common.aop.SaveRequestProcessor;
import com.visualp.common.security.impl.UserDetailsImpl;
import com.visualp.dwm.member.vo.MemberVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.common.security.impl
 * 3. 작성일     : 2020. 07. 14. 오후 12:01
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public class SecuritySession {

    static class NullMember extends MemberVO {
        public NullMember() {
        }
    }

    public static MemberVO getCurrentMember() {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return new NullMember();
        }

        Object pricial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (pricial instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) pricial).getMemberVO();
        }

        return new NullMember();
    }

    public static Authentication getAuthenCation() {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            return null;

        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

    @SuppressWarnings("deprecation")
    public static String getRealPath() {
        //StringBuffer sb = new StringBuffer();
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest().getRealPath("");
    }

    public static List<MultipartFile> getRequestToFile(HttpServletRequest request, String name) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multiRequest.getFiles(name);

        List<MultipartFile> list = new ArrayList<MultipartFile>();
        for (MultipartFile file : files) {
            if (file.getSize() > 0)
                list.add(file);
        }
        return list;
    }

    public static String getQuery() {
        String str = (String) getCurrentRequest().getSession().getAttribute(SaveRequestProcessor.SAVE_REQUEST);
        if (str == null) return "";
        return str;
    }

    /**
     * 권한을 가지고 있는지 체크
     *
     * @param role
     * @return
     */
    public static boolean hasRole(String role) {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            return false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static String getContextPath() {
        HttpServletRequest request = getCurrentRequest();
        String local = request.getContextPath();
        return local;
    }
}
