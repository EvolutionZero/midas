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
import com.zero.midas.utils.DailyUtils;
import com.zero.midas.utils.MAUtils;
import com.zero.midas.utils.WRUtils;

public class CalculateIndex {

	public void exec(){
		StockStorage stockStorage = new StockStorage();
		DailyStorage dailyStorage = new DailyStorage();
		List<Stock> tradableStock = stockStorage.queryTradableStock();
		for (Stock stock : tradableStock) {
			List<Daily> line = dailyStorage.queryByCode(stock.getCode());
			List<Daily> needCalcuteDaily = new LinkedList<>();
			for (Daily daily : line) {
				if(daily.getMa30() == null){
					needCalcuteDaily.add(daily);
				}
			}
			calcuteMa30(line, needCalcuteDaily);
			calcuteBoll(line, needCalcuteDaily);
			calcuteWRx(line, needCalcuteDaily);
			for (Daily daily : needCalcuteDaily) {
				daily.setUpdateTime(new Timestamp(new Date().getTime()));
			}
			dailyStorage.saveOrUpdate(needCalcuteDaily);
		}
	}
	
	private void calcuteMa30(List<Daily> line, List<Daily> needCalcuteDaily){
		for (Daily point : needCalcuteDaily) {
			point.setMa30(MAUtils.getMAx(DailyUtils.subLineBeforePoint(line, point, 30), 30));
		}
	}
	
	private void calcuteBoll(List<Daily> line, List<Daily> needCalcuteDaily){
		for (Daily point : needCalcuteDaily) {
			Boll boll = BollUtils.getBoll(DailyUtils.subLineBeforePoint(line, point, 20));
			point.setUpper(boll.getUpper());
			point.setLower(boll.getLower());
			point.setMiddle(boll.getMiddle());
			point.setPercentB(boll.getPercentB());
		}
	}
	
	private void calcuteWRx(List<Daily> line, List<Daily> needCalcuteDaily){
		for (Daily point : needCalcuteDaily) {
			point.setWr5(WRUtils.getWRx(DailyUtils.subLineBeforePoint(line, point, 5), 5));
			point.setWr10(WRUtils.getWRx(DailyUtils.subLineBeforePoint(line, point, 10), 10));
			point.setWr20(WRUtils.getWRx(DailyUtils.subLineBeforePoint(line, point, 20), 20));
			point.setWr30(WRUtils.getWRx(DailyUtils.subLineBeforePoint(line, point, 30), 30));
		}
	}
}
