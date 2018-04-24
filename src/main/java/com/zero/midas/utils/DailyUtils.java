package com.zero.midas.utils;

import java.util.LinkedList;
import java.util.List;

import com.zero.midas.bean.pojo.Daily;

public class DailyUtils {

	public static List<Daily> subLineBeforePoint(List<Daily> line, Daily point, int cnt){
		List<Daily> result = new LinkedList<>();
		int endIndex = findEndIndex(line, point);
		if(endIndex + 1 - cnt >= 0){
			for (int i = (endIndex + 1 - cnt); i < (endIndex + 1); i++) {
				result.add(line.get(i));
			}
			
		}
		return result;
	}
	
	private static int findEndIndex(List<Daily> line, Daily point){
		int idx = -1;
		for (int i = 0; i < line.size(); i++) {
			Daily daily = line.get(i);
			if(daily.getDate() != null && daily.getDate().equals(point.getDate())){
				idx = i;
				break;
			}
		}
		return idx;
	}
	
	public static List<Daily> getLastDailys(List<Daily> line, int cnt){
		List<Daily> result = new LinkedList<>();
		if(line.isEmpty() && line.size() - cnt > 0){
			return result;
		}
		for (int i = (line.size() - cnt); i < line.size(); i++) {
			result.add(line.get(i));
		}
		return result;
	}
}
