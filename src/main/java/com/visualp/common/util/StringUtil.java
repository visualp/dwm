package com.visualp.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. 프로젝트명 : sapp
 * 2. 패키지명   : com.visualp.common.util
 * 3. 작성일     : 2020. 07. 28. 오후 6:12
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
public class StringUtil {
    public static String getNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    /**
     * elastic search 용 ngram 2,3 자르기
     * @param str
     * @param n 자를 글자수
     * @return
     */
    public static String esNgram(String str, int n){
        StringTokenizer stringTokenizer = new StringTokenizer(str.trim()," ");
        List<String> strArr = new ArrayList<>();
        while(stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            for(int i=0; i<token.length(); i++){
                if(i+n > token.length()){
                    break;
                }
                strArr.add(token.substring(i,i+n));
            }
        }
        return String.join(" ", strArr);
    }
}
