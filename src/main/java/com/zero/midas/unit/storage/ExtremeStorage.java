package com.zero.midas.unit.storage;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.zero.midas.bean.pojo.Extreme;
import com.zero.midas.envm.ExtremeLevel;

public class ExtremeStorage extends FinanceBaseStorage<Extreme>{
	
	public Date queryLastDate(String code, ExtremeLevel level){
		Date result = null;
		String sql = "select max(date) lastDate from t_extreme where `code` = ? and `level` = ?";
		List<Map<String, Object>> list = queryForList(sql, new Object[]{code, level.getName()});
		if(!list.isEmpty()){
			Map<String, Object> map = list.get(0);
			result = map.get("lastDate") == null ? null : (Date)map.get("lastDate");
		}
		return result;
	}
	
	public List<Extreme> query(String code, ExtremeLevel level){
		String sql = query + " where `code` = ? and `level` = ?";
		return query(sql, new Object[]{code, level.getName()});
	}
}
