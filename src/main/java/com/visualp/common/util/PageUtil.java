package com.visualp.common.util;

import com.visualp.common.vo.BaseVO;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * jpa 대응 하도록 생성자 추가
 * 작성자 : 고병만
 * 작성일 : 2020-11-19
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageUtil extends BaseVO {

    //한 페이지 당 보여줄 글 갯수
    @Builder.Default
    private Integer pageSize = 15;

    //현제페이지 번호
    @Builder.Default
    private Integer page = 1;

    //이전 페이지
    private Integer prevPage;

    //다음페이지
    private Integer nextPage;

    //첫 번째 페이지 번호
    private Integer firstPage;

    //마지막 페이지 번호
    private Integer finalPage;

    //총페이지 수
    private Integer pageCount;

    //한페이지 시작 글  번호
    private Integer startRow;

    //한페이지 마지막 글 번호
    private Integer endRow;

    private Integer startPage;

    private Integer endPage;

    //전체  글 개수
    private Long totalCount;

    /**
     * 페이지 그룹 사이즈
     */
    @Builder.Default
    private Integer pageGroupSize=5;

    /**
     * 현제 페이지 그룹번호
     */
    private Integer numPageGroup;

    /**
     * 페이지 그룹 카운트
     */
    private Integer pageGroupCount;

    //이전 그룹(그룹의 페이지 시작 번호)
    private Integer prevGroupPage;

    //다음 그룹(시작 페이지 번호)
    private Integer nextGroupPage;

    //limit query start
    private Integer offset;

    //limit query end
    private Integer limit;

    //기본주소
    private String basePageUrl;
    //현제 페이지 주소
    private String currentPageUrl;

    //이전 페이지 주소
    private String prevPageUrl;

    //다음페이지 주소
    private String nextPageUrl;

    //이전 gorup 이동 페이지 주소
    private String prevGroupPageUrl;

    //다음 group 이동 페이지 주소
    private String nextGroupPageUrl;

    //처음페이지
    private String firstPageUrl;

    //마지막 페이지
    private String finalPageUrl;

    private String queryString;

    private String basePagingUrl;

    /**
     * @param page   //현제페이지
     * @param totalCount    //전체페이지 수
     * @param pageSize      // 페이징 사이즈 default: 15
     * @param pageGroupSize // 그룹 사이즈 default: 5
     */
    public PageUtil(Integer page, Long totalCount, Integer pageSize, Integer pageGroupSize) {
        this.page = page == null ? 1 : page;
        this.totalCount = totalCount;
        this.pageSize = pageSize == null ? 15 : pageSize;
        this.pageGroupSize = pageGroupSize == null ? 5 : pageGroupSize;
        init();
    }

    public PageUtil(Integer page, Integer pageSize, Integer pageGroupSize) {
        this.page = page == null ? 1 : page;
        this.pageSize = pageSize == null ? 15 : pageSize;
        this.pageGroupSize = pageGroupSize == null ? 5 : pageGroupSize;
    }

    public PageUtil(Page<?> pages){
        this.page = pages.getNumber()+1;
        this.totalCount = pages.getTotalElements();
        this.pageSize = pages.getSize();
        this.pageGroupSize=10;
        init();
    }

    //초기화
    public void init() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
        Enumeration enums = request.getParameterNames();

        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            if (!paramName.equals("page")) {
                // Parameter가 배열일 경우
                if (parameters.length > 1) {
                    parameterMap.put(paramName, parameters);
                    //Parameter가 배열이 아닌 경우
                } else {
                    parameterMap.put(paramName, parameters[0]);
                }
            }
        }

        int q = 0;
        for (Entry<Object, Object> entry : parameterMap.entrySet()) {

            String key = (String) entry.getKey();
            String value = "";
            value = URLEncoder.encode((String) entry.getValue(), StandardCharsets.UTF_8);

            if (q == 0) {
                queryString = "?" + key + "=" + value;
            } else {
                queryString += "&" + key + "=" + value;
            }
            q++;
        }

        if (queryString == null) {
            queryString = "?ff=1";
        }

        this.basePageUrl = request.getRequestURI().replace(request.getContextPath(), "");
        this.currentPageUrl = this.basePageUrl + queryString + "&page=" + page;

        startRow = Math.toIntExact(totalCount - (page - 1) * pageSize);

        endRow = startRow - pageSize + 1;

        pageCount = Math.toIntExact((totalCount / pageSize) + (totalCount % pageSize == 0 ? 0 : 1));

        numPageGroup = (int) Math.ceil((double) page / pageGroupSize);

        startPage = (numPageGroup - 1) * pageGroupSize + 1;
        endPage = startPage + pageGroupSize - 1;

        if (endPage > pageCount) {
            endPage = pageCount;
        }

        pageGroupCount = Math.toIntExact(totalCount / (pageSize * pageGroupSize) + (totalCount % (pageSize * pageGroupSize) == 0 ? 0 : 1));

        firstPage = 1;
        finalPage = Math.toIntExact((totalCount + (pageSize - 1)) / pageSize); // 마지막 페이지

        boolean isNowFirst = page == 1; // 시작 페이지 (전체)
        boolean isNowFinal = page == finalPage; // 마지막 페이지 (전체)

        if (isNowFirst) {
            this.setPrevPage(1);
        } else {
            this.setPrevPage(((page - 1) < 1 ? 1 : (page - 1))); // 이전 페이지 번호
        }

        if (isNowFinal) {
            this.setNextPage(finalPage); // 다음 페이지 번호
        } else {
            this.setNextPage(((page + 1) > finalPage ? finalPage : (page + 1))); // 다음 페이지 번호
        }

        if (numPageGroup > 1) {
            prevGroupPage = (numPageGroup - 2) * pageGroupSize + 1;
        }

        if (numPageGroup < pageGroupCount) {
            nextGroupPage = numPageGroup * pageGroupSize + 1;
        }

        //limit query
        this.offset = (this.page - 1) * this.pageSize;
        this.limit = this.pageSize;

        //url세팅
        this.basePageUrl = request.getRequestURI().replace(request.getContextPath(), "");

        this.basePagingUrl = basePageUrl + this.queryString;

        this.currentPageUrl = this.basePageUrl + this.queryString + "&page=" + this.page;
        this.firstPageUrl = this.basePageUrl + this.queryString + "&page=" + this.firstPage;
        this.finalPageUrl = this.basePageUrl + this.queryString + "&page=" + this.finalPage;

        if (nextPage != null) {
            nextPageUrl = basePageUrl + this.queryString + "&page=" + nextPage;
        }

        if (prevPage != null) {
            prevPageUrl = basePageUrl + this.queryString + "&page=" + prevPage;
        }

        if (prevGroupPage != null) {
            prevGroupPageUrl = basePageUrl + this.queryString + "&page=" + prevGroupPage;
        }

        if (nextGroupPage != null) {
            nextGroupPageUrl = basePageUrl + this.queryString + "&page=" + nextGroupPage;
        }
    }

}
