package com.zero.midas.utils;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import com.zero.midas.bean.pojo.Daily;
import com.zero.midas.bean.pojo.Extreme;

public class ExtremeUtils {

	
	public static List<Extreme> subLine(List<Extreme> line, int start){
		List<Extreme> result = new LinkedList<>();
		if(start > 0 && start < line.size()){
			for (int i = start; i < line.size(); i++) {
				result.add(line.get(i));
			}
		}
		return result;
	}
	
	public static List<Extreme> subLineBeforePoint(List<Extreme> line, Date end){
		List<Extreme> result = new LinkedList<>();
		for (int i = 0; i < line.size(); i++) {
			Extreme extreme = line.get(i);
			if(extreme.getDate() != null && extreme.getDate().before(end)){
				result.add(line.get(i));
			}
		}
		return result;
	}
//	
//	public static List<Daily> getLastDailys(List<Daily> line, int cnt){
//		List<Daily> result = new LinkedList<>();
//		if(line.isEmpty() && line.size() - cnt > 0){
//			return result;
//		}
//		for (int i = (line.size() - cnt); i < line.size(); i++) {
//			result.add(line.get(i));
//		}
//		return result;
//	}
}
