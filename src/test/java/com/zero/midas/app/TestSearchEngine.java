//package com.zero.midas.app;
//
//import java.util.List;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.zero.midas.Initialization;
//import com.zero.midas.bean.pojo.Stock;
//import com.zero.midas.unit.storage.StockStorage;
//import com.zero.midas.utils.SearchEngine;
//
//public class TestSearchEngine {
//
//	@BeforeClass
//	public static void init(){
//		Initialization.exec();
//	}
//
//	@Test
//	public void testDbSearch(){
////		long start = System.currentTimeMillis();
////		int testCnt = 1000;
//		StockStorage stockStorage = new StockStorage();
////		for (int i = 0; i < testCnt; i++) {
////			stockStorage.queryByKeywords("人工智能  广州");
////		}
////		long end = System.currentTimeMillis();
////		System.out.println("平均耗时:" + ((end - start) / testCnt) + " ms.");
//
//
//		List<Stock> stocks = stockStorage.queryByKeywords("雄安 人工智能");
//		for (Stock stock : stocks) {
//			System.out.println(stock);
//		}
//		List<Stock> tradableStock = stockStorage.queryTradableStock();
//
//		System.out.println(stockStorage.queryAbnormalTradeStatusStock().size());
////		System.out.println(tradableStock);
//	}
//
//	@Test
//	public void testMemorySearch(){
//		StockStorage stockStorage = new StockStorage();
//		List<Stock> tradableStock = stockStorage.queryTradableStock();
//		String keyword = "人工智能 体育";
//		int testCnt = 1000;
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < testCnt; i++) {
//			for (Stock stock : tradableStock) {
//				String industry = stock.getIndustry() == null ? "" : stock.getIndustry();
//				String location = stock.getLocation() == null ? "" : stock.getLocation();
//				String business = stock.getBusiness() == null ? "" : stock.getBusiness();
//				String concept = stock.getConcept() == null ? "" : stock.getConcept();
//				StringBuilder sb = new StringBuilder();
//				sb.append(industry).append(";").append(location).append(";").append(business).append(";").append(concept);
//				if(SearchEngine.isContainKeyword(sb.toString(), keyword)){
////					System.out.println(stock);
//				}
//			}
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("平均耗时:" + ((end - start) / testCnt) + " ms.");
//	}
//
//}
