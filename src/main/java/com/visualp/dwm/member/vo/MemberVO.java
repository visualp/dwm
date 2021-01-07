package com.visualp.dwm.member.vo;

import com.visualp.common.vo.BaseVO;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.member.entity
 * 3. 작성일     : 2021. 01. 04. 오전 11:33
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "tb_member")
public class MemberVO extends BaseVO {
    //회원번호_pk_auto_increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long mbNo;

    //회원아이디
    @Column(unique = true)
    @NonNull
    private  String mbId;

    //이름
    private  String mbName;

    //비밀번호
    private  String mbPasswd;

    //모바일번호
    private  String mbHp;

    //이메일
    private  String mbEmail;

    //가입일
    @CreatedDate
    private Date regDate;

    //마지막수정일
    @UpdateTimestamp
    private  Date upDate;

    //사용여부 1-> 사용 0-> 미사용
    @Column(columnDefinition = "tinyint")
    private  Integer mbFlag;

    @Transient
    @Builder.Default
    List<MemberRoleVO> memberRoleVOS = new ArrayList<>();

}
