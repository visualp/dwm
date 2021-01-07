package com.visualp.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 1. 프로젝트명 : icf
 * 2. 패키지명   : com.visualp.common.vo
 * 3. 작성일     : 2020. 08. 12. 오후 12:01
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCountVO {
    private Long totalCount;
    private List list;
}
