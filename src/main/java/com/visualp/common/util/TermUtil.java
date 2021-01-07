package com.visualp.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TermUtil {

	public final static HashMap<String, String> termdate = new HashMap<String, String>();
	static{

		termdate.put("1t_start", "1216");
		termdate.put("1t_end"  , "0115");

		termdate.put("2t_start", "0116");
		termdate.put("2t_end"  , "0215");

		termdate.put("3t_start", "0216");
		termdate.put("3t_end"  , "0315");

		termdate.put("4t_start", "0316");
		termdate.put("4t_end"  , "0415");

		termdate.put("5t_start", "0416");
		termdate.put("5t_end"  , "0515");

		termdate.put("6t_start", "0516");
		termdate.put("6t_end"  , "0615");

		termdate.put("7t_start", "0616");
		termdate.put("7t_end"  , "0715");

		termdate.put("8t_start", "0716");
		termdate.put("8t_end"  , "0815");

		termdate.put("9t_start", "0816");
		termdate.put("9t_end"  , "0915");

		termdate.put("10t_start", "0916");
		termdate.put("10t_end"  , "1015");

		termdate.put("11t_start", "1016");
		termdate.put("11t_end"  , "1115");

		termdate.put("12t_start", "1116");
		termdate.put("12t_end"  , "1215");

	}//end static

	/**
	 * 인수로 넘어온 날자기준으로 텀번호를 구한다.
	 * @param mmdd
	 * @return
	 */

	public static int check_term(String mmdd){

		int retval = 1;
		int cmmdd = Integer.parseInt(mmdd);

		for(int i=1; i<=12; i++){

			String st=i+"t_start";
			String ed=i+"t_end";

			int gst= Integer.parseInt( termdate.get(st) );
			int ged= Integer.parseInt( termdate.get(ed) );

			if( cmmdd <= ged  && cmmdd >= gst ){
				retval = i;
				break;
			}//end if
		}
		return retval;
	}//end method

	public static String term_start_date(String yyyymmdd){
		String retval= "";
		String tyear  =   yyyymmdd.substring(0, 4);
		String tmmdd  =   yyyymmdd.substring(4, 8);

		int tnum = check_term(tmmdd);

		if(tnum==1){
			if(Integer.parseInt(tmmdd) >= 1216 && Integer.parseInt(tmmdd) <= 1231){
				retval= Integer.parseInt(tyear) + termdate.get(tnum + "t_start");
			}else{
				retval= (Integer.parseInt(tyear) - 1) + termdate.get(tnum + "t_start");
			}
		}else{
			retval= Integer.parseInt(tyear) + termdate.get(tnum + "t_start");
		}
		return retval;
	}

	public static String term_end_date(String yyyymmdd){

		String retval= "";
		String tyear  =   yyyymmdd.substring(0, 4);
		String tmmdd  =   yyyymmdd.substring(4, 8);

		int tnum = check_term(tmmdd);
		if(tnum==1){
			if(Integer.parseInt(tmmdd) >= 1216 && Integer.parseInt(tmmdd) <= 1231){
				retval= (Integer.parseInt(tyear) + 1) + termdate.get(tnum + "t_end");
			}else{
				retval= Integer.parseInt(tyear) + termdate.get(tnum + "t_end");
			}
		}else{
			retval= Integer.parseInt(tyear) + termdate.get(tnum + "t_end");
		}

		return retval;
	}//end method

	public static HashMap termlist(String yyyy){

		int tyyyy = Integer.parseInt( yyyy );

		HashMap<String, String> list  = new HashMap<String, String>();

		for(int i=1; i<=12; i++){
			if(i==1){

				String tyear =  String.valueOf( (tyyyy-1) );
				list.put( i + "t_start" , tyear + termdate.get(i + "t_start") );
				list.put( i + "t_end"   , yyyy + termdate.get(i + "t_end") );

			}else if(i==12){

				String tyear =  String.valueOf( (tyyyy) );
				list.put( i + "t_start" , yyyy  + termdate.get(i + "t_start") );
				list.put( i + "t_end"   , tyear + termdate.get(i + "t_end")   );

			}else{
				list.put( i + "t_start" , yyyy  + termdate.get(i + "t_start") );
				list.put( i + "t_end"   , yyyy + termdate.get(i + "t_end") );
			}
		}

		return list;
	}

	/**
	 * MMdd 리스트 리턴
	 * @return
	 */
	public static List<HashMap> termlist(){

		List<HashMap> list = new ArrayList<HashMap>();
		HashMap<String, String> map = null;

		for(int i = 1 ; i <= 12 ; i++){
			map = new HashMap<String, String>();
			map.put("t", i + "");
			map.put("t_start" , termdate.get(i + "t_start"));
			map.put("t_end" , termdate.get(i + "t_end"));
			list.add(map);
		}

		return list;
	}
	public static String getTermStartDate(int term){
		return termdate.get(term + "t_start");
	}
	public static String getTermEndDate(int term){
		return termdate.get(term + "t_end");
	}

	/**
	 *
	 *<pre>
	 * 1. 메소드명 : getTargetYear
	 * 2. 작성일 : 2016. 12. 23. 오후 1:37:02
	 * 3. 작성자 : 고병만
	 * 4. 설명 : 일자로 해당 year 구하기
	 * @param yyyymmdd
	 * @return
	 *</pre>
	 *
	 */
	public static Integer getTargetYear(String yyyymmdd){
		Integer retval;
		int year = Integer.parseInt(yyyymmdd.substring(0,4));
		int mmdd = Integer.parseInt(yyyymmdd.substring(4));

		if(mmdd>=1216){
			retval = year+1;
		}else{
			retval = year;
		}

		return  retval;
	}

	/**
	 *  해달 날짜 기준 다음텀 구하기
	 * @param yyyymmdd
	 * @return
	 */
	public static HashMap<String, String> next_term(String yyyymmdd){
		HashMap<String, String> map = new HashMap<>();

		String mmdd = yyyymmdd.substring(4,8);
		Integer year  = Integer.parseInt(yyyymmdd.substring(0,4));
		Integer t = check_term(mmdd);

		String t_start="";
		String t_end="";

		if(t==12){
			t=1;
			t_start = year + getTermStartDate(t);
			t_end = (year + 1) + getTermEndDate(t);
		}else{
			t=t+1;
			if( Integer.parseInt(mmdd)>=1216 &&  Integer.parseInt(mmdd)<=1231 ){
				//12월
				t_start = (year + 1) + getTermStartDate(t);
				t_end = (year + 1) + getTermEndDate(t);
			}else{
				t_start = year + getTermStartDate(t);
				t_end = year + getTermEndDate(t);
			}
		}

		map.put("t",t.toString());
		map.put("t_start", t_start );
		map.put("t_end", t_end);

		return map;
	}

	/**
	 * 해당 날짜기준 이전텀 구하기
	 * @param yyyymmdd
	 * @return
	 */
	public static HashMap prev_term(String yyyymmdd){

		HashMap<String, String> map = new HashMap<>();
		String mmdd = yyyymmdd.substring(4,8);
		Integer year  = Integer.parseInt(yyyymmdd.substring(0,4));
		Integer t = check_term(mmdd);

		String t_start="";
		String t_end="";

		if(t==1){
			t=12;
			if(Integer.parseInt(mmdd)>=1216 && Integer.parseInt(mmdd)<=1231  ){
				//12월이면
				t_start = year + getTermStartDate(t);
				t_end = year + getTermEndDate(t);
			}else{
				t_start = (year - 1) + getTermStartDate(t);
				t_end = (year - 1) + getTermEndDate(t);
			}
		}else{
			t=t-1;
			if(t==1){
				t_start = (year - 1) + getTermStartDate(t);
				t_end = year + getTermEndDate(t);
			}else{
				t_start = year + getTermStartDate(t);
				t_end = year + getTermEndDate(t);
			}

		}
		map.put("t",t.toString());
		map.put("t_start", t_start );
		map.put("t_end", t_end);
		return map;
	}

	/**
	 *  카운트 변수 만큼 달 이동을 한 후 텀 시작일 구하기
	 * @param yyyymmdd
	 * @param count
	 * @return
	 */
	public static String term_start_date_count(String yyyymmdd, int count){

		HashMap tmap = null;
		if(count<0) {
			 tmap = prev_term(yyyymmdd);
			 count = count-1;
		}else{
			 tmap = next_term(yyyymmdd);
			 count = count+1;
		}

		Integer year = Integer.parseInt(tmap.get("t_start").toString().substring(0,4));
		Integer month = Integer.parseInt(tmap.get("t_start").toString().substring(4,6));
		Integer date  = Integer.parseInt(tmap.get("t_start").toString().substring(6,8));
		HashMap map = new HashMap();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.set(year,month,date);
		cal.add(Calendar.MONTH,count);
		String tdate = sdf.format(cal.getTime());

		return term_start_date(tdate);
	}
}
