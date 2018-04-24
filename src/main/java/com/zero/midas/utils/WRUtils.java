package com.zero.midas.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.zero.midas.bean.pojo.Daily;

public class WRUtils {
	
	
	public static double getWRx(List<Daily> dailys, int cycle){
		double wr = 0;
		if(dailys.size() < cycle){
			return wr;
		}
		double close = dailys.get(dailys.size() - 1).getClose();
		
		double[] highLine = new double[dailys.size()];
		double[] lowLine = new double[dailys.size()];
		for (int i = 0; i < dailys.size(); i++) {
			Daily daily = dailys.get(i);
			highLine[i] = daily.getHigh();
			lowLine[i] = daily.getLow();
		}
		double maxValue = MathUtils.getMaxValue(highLine);
		double minValue = MathUtils.getMinValue(lowLine);
		
		if(maxValue - minValue != 0){
			wr = (maxValue - close) / (maxValue - minValue) * 100;
		} else {
			wr = 0;
		}
		
		DecimalFormat df = new DecimalFormat("0.###");
		wr = Double.valueOf(df.format(wr));
		return wr;
	}
}
