package com.cgeel.common.utils;

public class TimeChange {

	public static String tochange(Long time) {
		if(time!=null){
			String times="";
			long time1 = DateUtils.getTimeMillisNow()-time;
			if(time1<60){
	    		times=time1+"秒钟前";
	    	}else if(60<=time1&&time1<3600){
	    		times=(time1/60)+"分钟前";
	    	}else if(3600<=time1&&time1<3600*24){
	    		times=(time1/3600)+"小时前";
	    	}else{
	    		times = DateUtils.getDatebyTimeMillis(time, "yyyy-MM-dd HH:mm:ss");
	    	}
			return times;
		}else{
			return "";
		}
		
	}
	
	public static String tochange2(Long time) {
		if(time!=null){
			return DateUtils.getDatebyTimeMillis(time, "yyyy-MM-dd HH:mm:ss");
		}else{
			return "";
		}
		
	}
}
