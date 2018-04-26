package com.zero.midas.app;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.bean.pojo.Daily;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.storage.DailyStorage;
import com.zero.midas.unit.storage.StockStorage;
import com.zero.midas.utils.HttpUtils;

public class StockDailyDataTask {

	private static final Logger LOG = LoggerFactory.getLogger(StockDailyDataTask.class);
	
	public void exec(){
		StockStorage stockStorage = new StockStorage();
		DailyStorage dailyStorage = new DailyStorage();
		List<Stock> tradableStock = stockStorage.queryCollectInfoStock();
		Map<String, Date> codeLastDateMap = dailyStorage.queryCodeLastDate();
		int threadSize = 4;
		ExecutorService mainThreadPool = Executors.newFixedThreadPool(threadSize);
		for (Stock stock : tradableStock) {
			Date date = codeLastDateMap.get(stock.getCode());
			if(date != null && date.toString().equals(new Date(new java.util.Date().getTime()).toString())){
				LOG.info("{}已有最新数据！", stock.getCode());
				continue;
			}
			mainThreadPool.execute(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					LOG.info("收集{}数据！", stock.getCode());
					List<Daily> result = new LinkedList<>();
					try {
						String url = "http://api.finance.ifeng.com/akdaily/?code="+ stock.getCode() + "&type=last" ;
						String json = HttpUtils.getText(url, "UTF-8");
						Map<?, ?> map = null;
						try {
							ObjectMapper mapper = new ObjectMapper();
							map = mapper.readValue(json, LinkedHashMap.class);
						} catch (Exception e) {
							LOG.error("", e);
						}
						Object record = map.get("record");
						if(record == null || "{}".equals(record.toString())){
							return ;
						}
						List<List<Object>> datas = (List<List<Object>> )record;
						for (List<Object> data : datas) {
							Daily daily = new Daily(stock.getCode(), data);
							result.add(daily);
						}
						
						Date date = codeLastDateMap.get(stock.getCode());
						if(date != null){
							result = filter(result, date);
						}
						dailyStorage.saveOnly(result);
					} catch (Exception e) {
						LOG.error("", e);
					}
					
				}
			});
		}
	}
	
	
	private List<Daily> filter(List<Daily> dailies, Date lastDate){
		List<Daily> result = new LinkedList<>();
		for (Daily daily : dailies) {
			if(daily.getDate().after(lastDate)){
				result.add(daily);
			}
		}
		return result;
	}
}
