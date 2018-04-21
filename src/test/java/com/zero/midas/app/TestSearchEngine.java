package com.zero.midas.app;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zero.midas.Initialization;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.storage.StockStorage;

public class TestSearchEngine {

	@BeforeClass
	public static void init(){
		Initialization.exec();
	}
	
	@Test
	public void testExec(){
		List<Stock> stocks = new StockStorage().queryByKeywords("人工智能  广州");
		for (Stock stock : stocks) {
			System.out.println(stock);
		}
	}
	
}
