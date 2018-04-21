package com.zero.midas.unit.storage;

import java.util.List;

import com.zero.midas.bean.pojo.Stock;

public class StockStorage extends FinanceBaseStorage<Stock>{

	public List<Stock> queryTradableStock(){
		String sql = query + " where type like '%A股%' OR  type like '%B股%' OR  type like '%创业板%'";
		return query(sql);
	}
}
