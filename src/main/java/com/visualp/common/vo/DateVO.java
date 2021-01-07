package com.visualp.common.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 1. 프로젝트명 : sapp
 * 2. 패키지명   : com.visualp.common.vo
 * 3. 작성일     : 2020. 07. 17. 오후 3:01
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Builder
@Data
public class DateVO {
    private String year;
    private String month;
    private String day;
    private String wday;
    private String cdate;
}
