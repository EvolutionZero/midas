package com.zero.midas;

import com.zero.midas.app.CalculateIndex;
import com.zero.midas.app.IntelligenceCollect;
import com.zero.midas.app.StockDailyDataTask;
import com.zero.midas.unit.collect.StockBaseInfoCollector;
import com.zero.midas.unit.storage.StockStorage;

public class Main {

	public static void main(String[] args) {
		StockStorage stockStorage = new StockStorage();
		
		// 基础信息采集
		new StockBaseInfoCollector().exec();
		
		// 基础数据采集
		new StockDailyDataTask().exec();
		
		// 更新正常交易
		stockStorage.updateNormalStock();
		
		// 计算指标
		new CalculateIndex().exec();
		
		// 全面信息采集
		new IntelligenceCollect().exec();
		
	}
}
