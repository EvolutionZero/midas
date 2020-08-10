//package com.zero.midas.utils;
//
//import java.text.DecimalFormat;
//import java.util.List;
//
//import com.zero.midas.bean.pojo.Daily;
//
//public class MAUtils {
//
//	public static double getMAx(List<Daily> dailys, int n){
//		if(dailys.size() < n){
//			return 0;
//		}
//		DecimalFormat df = new DecimalFormat("0.###");
//		double total = 0;
//		for (Daily daily : dailys) {
//			total += daily.getClose();
//		}
//		return Double.valueOf(df.format(total / n));
//	}
//
//}
