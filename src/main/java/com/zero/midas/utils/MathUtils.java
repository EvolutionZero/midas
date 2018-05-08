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
	
	
	/**
	 * 取最大值
	 * @param values
	 * @return
	 */
	public static double getMaxValue(double... values){
		double max = Double.MIN_VALUE;
		for (double value : values) {
			max = value > max ? value : max;
		}
		return max;
	}
	
	/**
	 * 取最小值
	 * @param values
	 * @return
	 */
	public static double getMinValue(double... values){
		double min = Double.MAX_VALUE;
		for (double value : values) {
			min = value < min ? value : min;
		}
		return min;
	}
	
	/**
	 * 是否顶部
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isTop(double x, double y, double z){
		return y > x && y > z;
	}
	
	/**
	 * 是否底部
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isButtom(double x, double y, double z){
		return y < x && y < z;
	}
}
