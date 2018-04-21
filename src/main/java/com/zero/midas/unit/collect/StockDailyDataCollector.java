package com.zero.midas.unit.collect;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.bean.pojo.Daily;
import com.zero.midas.utils.HttpUtils;

public class StockDailyDataCollector implements Runnable, Callable<List<Daily>> {
	
	private static final Logger LOG = LoggerFactory.getLogger(StockDailyDataCollector.class);
	
	private String code;
	
	public StockDailyDataCollector(String code) {
		super();
		this.code = code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Daily> call() throws Exception {
		List<Daily> result = new LinkedList<>();
		try {
			String url = "http://api.finance.ifeng.com/akdaily/?code="+ code + "&type=last" ;
			String json = HttpUtils.getText(url, "UTF-8");
			Map<?, ?> map = null;
			try {
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(json, LinkedHashMap.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Object record = map.get("record");
			if(record == null || "{}".equals(record.toString())){
				return result;
			}
			List<List<Object>> datas = (List<List<Object>> )record;
			for (List<Object> data : datas) {
				Daily daily = new Daily(code, data);
				result.add(daily);
			}
		} catch (Exception e) {
			LOG.error("", e);
		}
		return result;
	}

	@Override
	public void run() {
		try {
			call();
		} catch (Exception e) {
			LOG.error("", e);
		}
		
	}
}
