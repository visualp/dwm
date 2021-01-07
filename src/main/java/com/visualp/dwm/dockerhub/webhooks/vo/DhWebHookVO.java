package com.visualp.dwm.dockerhub.webhooks.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.visualp.common.vo.BaseVO;
import com.visualp.dwm.project.vo.ProjectVO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.dockerhub.webhooks.vo
 * 3. 작성일     : 2021. 01. 05. 오전 9:38
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "tb_docker_webhooks")
public class DhWebHookVO extends BaseVO {

    //ai
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long dwNo;

    //projectNo(FK)
    private  Long prjNo;

    //webhook(json)
    @Column(columnDefinition = "text")
    private  String dwJsonData;

    @CreationTimestamp
    private Date regDatetime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prjNo" , referencedColumnName ="prjNo",foreignKey = @ForeignKey(name = "fk_tdw_prj_no"), insertable = false, updatable = false)
    private ProjectVO projectVO;

}
