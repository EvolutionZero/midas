package com.zero.midas.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zero.midas.Initialization;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.storage.StockStorage;

public class SearchEngine {

	public static void main(String[] args) {
		Initialization.exec();
		StockStorage stockStorage = new StockStorage();
		List<Stock> tradableStock = stockStorage.queryTradableStock();
		String keyword = "人工智能 体育";
		for (Stock stock : tradableStock) {
			String business = stock.getBusiness() == null ? "" : stock.getBusiness();
			String concept = stock.getConcept() == null ? "" : stock.getConcept();
			StringBuilder sb = new StringBuilder();
			sb.append(business).append(";").append(concept);
			if(isContainKeyword(sb.toString(), keyword)){
				System.out.println(stock);
			}
		}
	}
	
	public static boolean isContainKeyword(String text, String keyword){
		String[] array = keyword.trim().split(" ");
		Set<String> keywords = new HashSet<>();
		for (String temp : array) {
			keywords.add(temp);
		}
		boolean contain = false;
		int expect = keywords.size();
		if(expect == 0){
			return contain;
		}
		int actual = 0;
		for (String kw : keywords) {
			if(text.contains(kw)){
				actual++;
			}
		}
		if(actual == expect){
			contain = true;
		}
		return contain;
	}
}
