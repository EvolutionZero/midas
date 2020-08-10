//package com.zero.midas.app;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import com.zero.midas.bean.pojo.Concept;
//import com.zero.midas.bean.pojo.Stock;
//import com.zero.midas.unit.storage.ConceptStorage;
//import com.zero.midas.unit.storage.StockStorage;
//
//public class ConceptHandler {
//
//	public void exec(){
//		StockStorage stockStorage = new StockStorage();
//		List<Stock> stocks = stockStorage.queryAllConcept();
//		ConceptStorage conceptStorage = new ConceptStorage();
//		for (Stock stock : stocks) {
//			List<Concept> conceptBeans = new LinkedList<>();
//			String[] concepts = stock.getConcept().split(",");
//			for (String concept : concepts) {
//				if(!"".equals(concept)){
//					Concept bean = new Concept();
//					bean.setCode(stock.getCode());
//					bean.setConcept(concept);
//
//					conceptBeans.add(bean);
//
//				}
//			}
//			conceptStorage.saveUnique(conceptBeans);
//		}
//	}
//
//}
