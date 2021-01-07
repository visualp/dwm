package com.visualp.common.util;

import com.visualp.common.aop.SaveRequestProcessor;
import com.visualp.common.aop.SaveURLProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TagUtils {

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

    public static String query() {
        String str = (String) getCurrentRequest().getSession().getAttribute(SaveRequestProcessor.SAVE_REQUEST);
        if (str == null) return "";
        return str;
    }

    public static String url() {
        String str = (String) getCurrentRequest().getSession().getAttribute(SaveURLProcessor.SAVE_URL);
        if (str == null) {
            return "";
        }
        return str;
    }

    public static HashMap imageResize(int w, int h, int imageW, int imageH) {

        HashMap<String, Integer> map = new HashMap<>();

        int maxWidth = w;
        int maxHeight = h;
        int width = imageW;
        int height = imageH;
        double ratio = 0;

        if (width > maxWidth) {
            ratio = Math.round(((double) maxWidth / (double) width) * 100) / 100.0;
            map.put("w", maxWidth);
            map.put("h", (int) (height * ratio));
            height = (int) (height * ratio);
        }

        width = imageW;
        height = imageH;

        if (height > maxHeight) {
            ratio = Math.round(((double) maxHeight / (double) height) * 100) / 100.0;
            map.put("h", maxHeight);
            map.put("w", (int) (width * ratio));
        }

        return map;
    }

    /**
     * 초 를 HH:mm:ss 형식으로 변환
     *
     * @param s
     * @return
     */
    public static String secondParse(int s) {
        int hours, minute, second;

        hours = s / 3600; // 시간
        minute = s % 3600 / 60; // 분
        second = s % 3600 % 60; // 초

        String s_hours = hours < 10 ? "0" + hours : hours + "";
        String s_minute = minute < 10 ? "0" + minute : minute + "";
        String s_second = second < 10 ? "0" + second : second + "";

        return s_hours + ":" + s_minute + ":" + s_second;
    }

    /**
     * nl2br textarea to <br>
     */
    public static String nl2br(String str) {
        if (str != null) {
            str = str.replaceAll("\r\n", "<br/>");
            str = str.replaceAll("\r", "<br/>");
            str = str.replaceAll("\n", "<br/>");
        }
        return str;
    }

    /**
     * br2nl textarea to nl
     */
    public static String br2nl(String str) {
        if (str != null) {
            str = str.replaceAll("<br/>", "\r\n");
            str = str.replaceAll("<br/>", "\r");
            str = str.replaceAll("<br/>", "\n");
        }
        return str;
    }

    /**
     * 문자 타입 날짜를 데이트 형으로 변환
     *
     * @param _date
     * @param format
     * @return
     */
    public static Date strTodate(String _date, String format) {
        if (format == null || _date == null || format.equals("") || _date.equals("")) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return date;
    }

    /**
     * yyyy-mm-dd 형식으로 표현
     *
     * @param yyyymmdd
     * @param sp
     * @return
     */
    public static String yyyymmddToStr(String yyyymmdd, String sp) {
        return yyyymmdd.substring(0, 4) + sp + yyyymmdd.substring(4, 6) + sp + yyyymmdd.substring(6, 8);
    }

    public static String mask_name(String name) {
        String retval = "";
        name = name.trim();
        if (name.length() <= 2) {
            retval = name.replaceAll("(?<=.{1}).", "*");
        } else if (name.length() == 3) {
            retval = name.replaceAll("(?<=.{2}).", "*");
        } else if (name.length() == 4) {
            retval = name.replaceAll("(?<=.{3}).", "*");
        } else {
            retval = name.replaceAll("(?<=.{4}).", "*");
        }
        return retval;
    }

    /**
     * 핸드폰번호 마스킹 처리
     *
     * @param mobile
     * @return
     */
    public static String mask_mobile(String mobile) {
        /*
         * 요구되는 휴대폰 번호 포맷
         * 01055557777 또는 0113339999 로 010+네자리+네자리 또는 011~019+세자리+네자리 이!지!만!
         * 사실 0107770000 과 01188884444 같이 가운데 번호는 3자리 또는 4자리면 돈케어
         * */
        String regex = "(01[016789])(\\d{3,4})\\d{4}$";

        Matcher matcher = Pattern.compile(regex).matcher(mobile);
        if (matcher.find()) {
            String replaceTarget = matcher.group(2);
            char[] c = new char[replaceTarget.length()];
            Arrays.fill(c, '*');
            return mobile.replace(replaceTarget, String.valueOf(c));
        }
        return mobile;
    }

    public static String mask_tel(String tel) {
        if (tel.length() > 3) {
            tel = tel.substring(0, tel.length() - 4) + "****";
        }
        return tel;
    }

    /**
     * 카드번호 마스킹 처리
     *
     * @param cardNumber
     * @return
     */
    public static String mask_card(String cardNumber) {

        if (cardNumber == null) {
            return cardNumber;
        } else if (cardNumber.equals("")) {
            return cardNumber;
        }
        String regex = "";
        if (cardNumber.length() == 16) {
            regex = "(\\d{4})(\\d{4})(\\d{4})(\\d{3,4})";
            cardNumber = cardNumber.replaceAll(regex, "$1 - **** - $3 - ****");
        } else {
            regex = "(\\d{4})(\\d{4})(\\d{4})(\\d{3,4})";
            cardNumber = cardNumber.replaceAll(regex, "$1 - **** - $3 - ****");
        }

        return cardNumber;
    }

    public static String mask_bank(String bankNumber) {
        String retval = "";
        retval = bankNumber.replaceAll("(?<=.{8}).", "*");
        return retval;
    }

    /**
     * 인코딩
     *
     * @param str 인코딩 대상 문자열
     * @param enc charset
     * @return
     */
    public static String urlencode(String str, String enc) {
        try {
            str = URLEncoder.encode(str, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
