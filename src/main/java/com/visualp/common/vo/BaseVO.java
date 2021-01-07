package com.visualp.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 1. 프로젝트명 : main
 * 2. 패키지명   : com.visualp.common.vo
 * 3. 작성일     : 2020. 07. 13. 오후 12:02
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

@JsonIgnoreProperties(value={"hibernateLazyInitializer"},ignoreUnknown = true)
public class BaseVO implements Serializable {

    private String redirect; // redirect url 저장 변수
    private HashMap<String, Object> temp = new HashMap<String, Object>();

    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";

    /**
     * insert/update/delete지정
     */
    private String action;

    /**
     * action에 따른 URL 지정
     */
    private String actionUrl;

    public HashMap<String, Object> getTemp() {
        return temp;
    }

    public void setTemp(HashMap<String, Object> temp) {
        this.temp = temp;
    }

    /**
     * <pre>
     * 1. 메소드명 : put_temp
     * 2. 작성일 : 2016. 3. 18. 오후 1:57:43
     * 3. 작성자 : 고병만
     * 4. 설명 :  HashMap 에 데이터 저장
     * @param key
     * @param value
     * </pre>
     */

    public void put_temp(String key, Object value) {
        temp.put(key, value);
    }

    /**
     * <pre>
     * 1. 메소드명 : remove_temp
     * 2. 작성일 : 2016. 3. 18. 오후 1:58:06
     * 3. 작성자 : 고병만
     * 4. 설명 : HashMap에 데이터 삭제
     * @param key
     * </pre>
     */
    public void remove_temp(String key) {
        temp.remove(key);
    }

    /**
     * <pre>
     * 1. 메소드명 : remove_temp
     * 2. 작성일 : 2016. 3. 18. 오후 1:58:06
     * 3. 작성자 : 고병만
     * 4. 설명 : VO객체의 값을 화인 할 때 사용
     * </pre>
     */

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

}
