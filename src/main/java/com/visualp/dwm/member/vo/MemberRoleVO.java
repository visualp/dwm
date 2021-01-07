package com.visualp.dwm.member.vo;

import lombok.*;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.member.vo
 * 3. 작성일     : 2021. 01. 04. 오후 12:08
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MemberRoleVO {
    //롤 아이디
    private String roId;
    //롤 네임
    private String roName;
}
