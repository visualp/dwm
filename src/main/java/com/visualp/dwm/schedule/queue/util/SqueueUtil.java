package com.visualp.dwm.schedule.queue.util;

import com.visualp.dwm.schedule.queue.vo.SqueueVO;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.schedule.queue.util
 * 3. 작성일     : 2021. 01. 06. 오전 9:57
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

public class SqueueUtil {

    public static void addNo(Long jobNo){
        SqueueVO.queue.addLast(jobNo);
    }

    public static Long getNo(){
        if(SqueueVO.queue.size()>0) {
            Long jobNo = SqueueVO.queue.getFirst();
            SqueueVO.queue.removeFirst();
            return jobNo;
        }
        return null;
    }

    public static boolean doing(){
        if(SqueueVO.currentJobNo == null ){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 현재 작업중인 queue 초기화
     */
    public static void init_currentJobNo(){
        SqueueVO.currentJobNo = null;
    }

    /**
     * 현재 작업중인 jobNo설정
     * @param jobNo
     */
    public static void setCurrentJobNo(Long jobNo){
        SqueueVO.currentJobNo = jobNo;
    }

}
