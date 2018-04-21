package com.zero.midas.unit.storage;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.zero.midas.bean.pojo.Stock;

public class StockStorage extends FinanceBaseStorage<Stock>{

	public List<Stock> queryTradableStock(){
		String sql = query + " where type like '%A股%' OR  type like '%B股%' OR  type like '%创业板%'";
		return query(sql);
	}
	
	public List<Stock> queryByKeywords(String keyword){
		String[] array = keyword.trim().split(" ");
		Set<String> keywords = new HashSet<>();
		for (String temp : array) {
			if(!"".equals(temp.trim())){
				keywords.add(temp);
			}
		}
		return queryByKeywords(keywords);
	}
	
	public List<Stock> queryByKeywords(Collection<String> keywords){
		if(keywords == null || keywords.isEmpty()){
			return new LinkedList<Stock>();
		}
		StringBuilder sql = new StringBuilder(query).append(" WHERE ( type like '%A股%' OR  type like '%B股%' OR  type like '%创业板%' ) AND ");
		List<Object> paramList = new LinkedList<Object>();
		for (String keyword : keywords) {
			sql.append(" ( industry like CONCAT('%', ?, '%') OR");
			sql.append(" concept like CONCAT('%', ?, '%') OR");
			sql.append(" location like CONCAT('%', ?, '%') OR");
			sql.append(" business like CONCAT('%', ?, '%') )");
			sql.append(" AND ");
			
			paramList.add(keyword);
			paramList.add(keyword);
			paramList.add(keyword);
			paramList.add(keyword);
		}
		sql.delete(sql.length() - " AND ".length(), sql.length());
		return query(sql.toString(), toArray(paramList));
	}
}
