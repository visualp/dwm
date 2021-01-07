package com.visualp.dwm.deploy.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.visualp.common.vo.BaseVO;
import com.visualp.dwm.project.vo.ProjectVO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.util.Date;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.deploy.vo
 * 3. 작성일     : 2021. 01. 05. 오후 1:04
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "tb_deploy")
public class DeployVO extends BaseVO {

    //배포번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long dpNo;
    //프로젝트 번호
    private Long prjNo;

    //실행 명령어
    private  String prjCmd;

    //실행로그
    @Column(columnDefinition = "text")
    private  String dpLog;

    //1->성공 , 0->실패
    @Column(columnDefinition = "tinyint")
    private  Integer dpSflag;

    //대기,진행,완료
    private String dpStatus;

    //등록자_아이디
    private  String regMbId;

    //등록자_이름
    private  String regMbName;

    //등록시간
    @CreationTimestamp
    private  Date regDatetime;

    //패치완료시간
    private Date resDatetime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "prjNo" , referencedColumnName ="prjNo",foreignKey = @ForeignKey(name = "FK_td_prj_no"), insertable = false, updatable = false)
    private ProjectVO projectVO;

    @Transient
    private String logLink;
}
