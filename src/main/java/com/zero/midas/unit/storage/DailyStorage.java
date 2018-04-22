package com.zero.midas.unit.storage;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zero.midas.bean.pojo.Daily;

public class DailyStorage extends FinanceBaseStorage<Daily>{
	
	public Map<String, Date> queryCodeLastDate(){
		Map<String, Date> result = new HashMap<>();
		String sql = "select code,max(date) lastDate from t_daily group by code";
		List<Map<String,Object>> list = queryForList(sql);
		for (Map<String,Object> map : list) {
			String code = map.get("code") == null ? "" : (String)map.get("code");
			Date lastDate = map.get("lastDate") == null ? null : (Date)map.get("lastDate");
			
			result.put(code, lastDate);
		}
		return result;
	}
	
	public List<Daily> queryByCode(String code){
		String sql = query + " where `code` = ? order by date";
		return query(sql, new Object[]{code});
	}
}
