package com.visualp.dwm.schedule.queue.vo;

import java.util.LinkedList;

/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.visualp.dwm.schedule.queue.vo
 * 3. 작성일     : 2021. 01. 06. 오전 9:42
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */

public class SqueueVO {

    public static volatile   LinkedList<Long> queue = new LinkedList<>();

    public static volatile Long currentJobNo;

}
