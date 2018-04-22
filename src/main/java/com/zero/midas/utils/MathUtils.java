package com.zero.midas.utils;

public class MathUtils {

	/**
	 * 计算标准差
	 * @param avg
	 * @param datas
	 * @return
	 */
	public static double getStandardDeviation(double avg , double[] datas){
		double standardDeviation = 0;
		if(datas.length > 0){
			double total = 0;
			for (double data : datas) {
				total += Math.pow(data - avg, 2);
			}
			standardDeviation = Math.sqrt(total / datas.length);
		}
		return standardDeviation;
	}
	
}
