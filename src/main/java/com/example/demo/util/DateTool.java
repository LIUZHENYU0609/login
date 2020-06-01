package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateTool {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat FORMAT_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * 字符串格式转换为date类型
	 * @param strFormat
	 * @return
	 */
	 public static Date stringToDate(String strFormat) {
 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

 		Date date  = null;
	        try {  
	             date = df.parse(strFormat);   
	        } catch (ParseException e) {
	            e.printStackTrace();  
	        }  
	         return date;  
	    }

	//   当前日期加减n天后的日期，返回String   (yyyy-mm-dd)   
	public  static String nDaysAftertoday(int n)   {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		//rightNow.add(Calendar.DAY_OF_MONTH,-1);   
		rightNow.add(Calendar.DAY_OF_MONTH,+n);
		return df.format(rightNow.getTime());
	}   

	/**
	 * 取得字符串格式的当前日期
	 * 
	 * @return
	 */
	public static String getCurrDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * 获取星期（中文如：‘星期一’）
	 * @param date
	 * @return
	 *String
	 *
	 */
	public static String getWeekdayString(String date){//必须yyyy-MM-dd
	    
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	    
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		Date d = null;
		
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
		return sdw.format(d);
	}
	

	/**
	 * 格式化时间
	 * @param format
	 * @param date
	 * @return
	 */
	public static String format(String format, Date date){
		if (null==date) {
			return null;
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static List<HashMap<String, String>> endDateListMap;
	public static List<HashMap<String, String>> endTimeListMap;
	static {
		initEndDateListMap();
		initEndTimeListMap();
	}

	private static void initEndDateListMap(){
		
		endDateListMap = new ArrayList<HashMap<String, String>>();
		
		for(int day=1; day<8; day++){
			HashMap<String, String> endDateMap = new HashMap<String, String>();
			
			String currDate = DateTool.nDaysAftertoday(day-1);
			endDateMap.put("date", currDate);
			endDateMap.put("desc", DateTool.getWeekdayString(currDate));
			endDateListMap.add(endDateMap);
		}
	}
	
	
	private static void initEndTimeListMap(){
		
		endTimeListMap = new ArrayList<HashMap<String, String>>();
		
		for(int hour=0; hour<24; hour++){
			HashMap<String, String> wholeVoteEndTimeMap = new HashMap<String, String>();
			HashMap<String, String> halfHourVoteEndTimeMap = new HashMap<String, String>();
			
			String desc = getCurrentEndTimeDesc(hour);
			
			wholeVoteEndTimeMap.put("desc", desc);
			halfHourVoteEndTimeMap.put("desc", desc);
			if(hour<10){
				wholeVoteEndTimeMap.put("time", "0" + hour + ":00" );
				halfHourVoteEndTimeMap.put("time", "0" + hour + ":30" );
			}else{
				wholeVoteEndTimeMap.put("time", hour + ":00" );
				halfHourVoteEndTimeMap.put("time", hour + ":30" );
			}
			endTimeListMap.add(wholeVoteEndTimeMap);
			endTimeListMap.add(halfHourVoteEndTimeMap);
		}
	}
	
	
	private static String getCurrentEndTimeDesc(int hour){
		
		return hour > 12 ? "下午" : "上午";
	}

	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

}
