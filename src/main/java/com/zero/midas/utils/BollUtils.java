package com.zero.midas.utils;

import java.text.DecimalFormat;
import java.util.List;

import com.zero.midas.bean.bean.Boll;
import com.zero.midas.bean.pojo.Daily;

public class BollUtils {

	public static Boll getBoll(List<Daily> dailys){
		Boll boll = new Boll();
		if(dailys.size() < 20){
			return boll;
		}
		double[] closeLine = new double[20]; 
		for (int i = (dailys.size() > 20 ? 19 : dailys.size() - 1); i >= 0; i--) {
			closeLine[i] = dailys.get(i).getClose();
		}
		
		double middle = dailys.get(dailys.size() - 1).getMa20();
		double standardDeviation = MathUtils.getStandardDeviation(middle, closeLine);
		double upper = middle + 2 * standardDeviation;
		double lower = middle - 2 * standardDeviation;
		
		DecimalFormat df = new DecimalFormat("0.###");
		upper = Double.valueOf(df.format(upper));
		lower = Double.valueOf(df.format(lower));
		
		double close =  dailys.get(dailys.size() - 1).getClose();
		double percentB = 0; 
		if((upper - lower) > 0.000001){
			percentB = (close - lower) / (upper - lower); 
			
		}
		percentB = Double.valueOf(df.format(percentB));
		
		
		boll.setLower(lower);
		boll.setUpper(upper);
		boll.setMiddle(middle);
		boll.setPercentB(percentB);
		return boll;
	}
}
