package com.visualp.dwm.project.vo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.visualp.common.vo.BaseVO;
import com.visualp.dwm.deploy.vo.DeployVO;
import com.visualp.dwm.dockerhub.webhooks.vo.DhWebHookVO;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.project.vo
 * 3. 작성일     : 2021. 01. 04. 오후 3:03
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_project",
    indexes = {
        @Index(name = "prj_code", columnList = "prjCode", unique = true)
    }
)
public class ProjectVO extends BaseVO {

    //프로젝트_번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long prjNo;

    private String prjCode;

    //프로젝트_이름
    private  String prjName;

    //원격서버_주소
    private  String prjRaddress;

    //원격서버_아이디
    private  String prjRid;

    //원격서버_비밀번호
    private  String prjRpassword;

    //사용여부 1-사용 0->미사용
    @Column(columnDefinition = "tinyint")
    @Builder.Default
    private  Integer prjFlag=1;

    //실행 명령어
    private String prjCmd;

    //등록자_아이디
    private  String regMbId;

    //등록자_이름
    private  String regMbName;

    //등록일자
    @CreationTimestamp
    private  Date regDatetime;

    //수정자_아이디
    private  String upMbId;

    //수정자_이름
    private  String upMbName;

    //수정일자
    @UpdateTimestamp
    private  Date upDatetime;

    @ToString.Exclude
    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy ="projectVO", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    List<DhWebHookVO> dhWebHookVOS = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "projectVO", fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    List<DeployVO> deployVOS = new ArrayList<>();

    @Transient
    private String whookAddress;

    /**
     * webhook log 등록
     * @param dhWebHookVO
     */
    public void add_dhWebHoosVO(DhWebHookVO dhWebHookVO){
        dhWebHookVO.setPrjNo(prjNo);
        this.getDhWebHookVOS().add(dhWebHookVO);
    }

    public void add_deployVO(DeployVO deployVO){
        deployVO.setPrjNo(prjNo);
        this.getDeployVOS().add(deployVO);
    }

}
