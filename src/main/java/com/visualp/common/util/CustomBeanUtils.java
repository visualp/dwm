package com.visualp.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * 1. 프로젝트명 : api
 * 2. 패키지명   : com.visualp.common.util
 * 3. 작성일     : 2020. 07. 02. 오후 3:24
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 * 6  연락처     : 010-8299-5258
 * [참고] https://cnpnote.tistory.com/entry/SPRING-springframework-BeanUtils-copyProperties%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-null-%EA%B0%92%EC%9D%84-%EB%AC%B4%EC%8B%9C%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95
 */
public class CustomBeanUtils {

    public static String[] getNullAndBlankPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (StringUtils.isEmpty(srcValue)) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * null 값과 공백을 무시함
     *
     * @param src
     * @param target
     */
    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullAndBlankPropertyNames(src));
    }

}
