package com.zero.midas.app;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.zero.midas.bean.bean.Boll;
import com.zero.midas.bean.pojo.Daily;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.storage.DailyStorage;
import com.zero.midas.unit.storage.StockStorage;
import com.zero.midas.utils.BollUtils;
import com.zero.midas.utils.MAUtils;

public class CalculateIndex {

	public void exec(){
		StockStorage stockStorage = new StockStorage();
		DailyStorage dailyStorage = new DailyStorage();
		List<Stock> tradableStock = stockStorage.queryTradableStock();
		for (Stock stock : tradableStock) {
			List<Daily> totalDailyDatas = dailyStorage.queryByCode(stock.getCode());
			List<Daily> needCalcuteDaily = new LinkedList<>();
			for (Daily daily : totalDailyDatas) {
				if(daily.getMa30() == null){
					needCalcuteDaily.add(daily);
				}
			}
			calcuteMa30(totalDailyDatas, needCalcuteDaily);
			calcuteBoll(totalDailyDatas, needCalcuteDaily);
			for (Daily daily : needCalcuteDaily) {
				daily.setUpdateTime(new Timestamp(new Date().getTime()));
			}
			dailyStorage.saveOrUpdate(needCalcuteDaily);
		}
	}
	
	private void calcuteMa30(List<Daily> totalDailyDatas, List<Daily> needCalcuteDaily){
		for (Daily daily : needCalcuteDaily) {
			int endIndex = findEndIndex(totalDailyDatas, daily);
			if(endIndex < 30 || totalDailyDatas.size() < 30){
				daily.setMa30(0.0);
			} else {
				List<Daily> calcuteMa30Line = getLine(totalDailyDatas, endIndex, 30);
				double ma30 = MAUtils.getMAx(calcuteMa30Line, 30);
				daily.setMa30(ma30);
			}
		}
	}
	
	private void calcuteBoll(List<Daily> totalDailyDatas, List<Daily> needCalcuteDaily){
		for (Daily daily : needCalcuteDaily) {
			int endIndex = findEndIndex(totalDailyDatas, daily);
			if(endIndex < 20 || totalDailyDatas.size() < 20){
				daily.setUpper(0.0);
				daily.setLower(0.0);
				daily.setMiddle(0.0);
				daily.setPercentB(0.0);
			} else {
				List<Daily> calcuteBollLine = getLine(totalDailyDatas, endIndex, 20);
				Boll boll = BollUtils.getBoll(calcuteBollLine);
				daily.setUpper(boll.getUpper());
				daily.setLower(boll.getLower());
				daily.setMiddle(boll.getMiddle());
				daily.setPercentB(boll.getPercentB());
			}
		}
	}
	
	private int findEndIndex(List<Daily> dailyDatas, Daily needCalcute){
		int idx = 0;
		for (int i = 0; i < dailyDatas.size(); i++) {
			Daily daily = dailyDatas.get(i);
			if(daily.getDate() != null && daily.getDate().equals(needCalcute.getDate())){
				idx = i;
				break;
			}
		}
		return idx;
	}
	
	private List<Daily> getLine(List<Daily> dailyDatas, int endIndex, int cnt){
		List<Daily> line = new LinkedList<>();
		for (int i = (endIndex + 1 - cnt); i < (endIndex + 1); i++) {
			line.add(dailyDatas.get(i));
		}
		return line;
	}
}
