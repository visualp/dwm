package com.visualp.common.vo;


import lombok.Builder;
import lombok.Data;

/**
 * 1. 프로젝트명 : sales
 * 2. 패키지명   : com.visualp.common.service
 * 3. 작성일     : 2018. 07. 16. 오전 10:38
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Data
@Builder
public class RedirectVO {

    public static final String URL_ALERT_AND_REDIRECT = "redirect:/common/redirect.html";
    public static final String URL_ALERT_AND_PARENT_REDIRECT = "redirect:/common/parent_redirect.html";
    public static final String MSG_OK = "정상 처리 되었습니다.";

    public static final String MODE_REDIRECT = "redirect";
    public static final String MODE_PREDIRECT = "predirect";


    //메시지
    private String message;

    //리텐 url
    private String url;
    //리턴 url 이후 리턴될 url ex) 로그인 이후 -> 이동할 페이지 처리
    private String murl;

    //리다이렌트 유형
    private String mode;

    public String getMode() {
        return mode == null ? MODE_REDIRECT : MODE_PREDIRECT;
    }
}
