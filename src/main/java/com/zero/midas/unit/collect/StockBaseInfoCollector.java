//package com.zero.midas.unit.collect;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.zero.midas.bean.pojo.Stock;
//import com.zero.midas.utils.HttpUtils;
//import com.zero.midas.utils.StockCodeUtils;
//
//public class StockBaseInfoCollector {
//
//	private static final Logger LOG = LoggerFactory.getLogger(StockBaseInfoCollector.class);
//
//	public List<Stock> exec(){
//		List<Stock> stocks = new LinkedList<>();
//		try {
//			String html = HttpUtils.getText("http://quote.eastmoney.com/stocklist.html", "GBK");
//			Document doc = Jsoup.parse(html);
//			Elements quotesearch = doc.select("#quotesearch");
//			Elements uls = quotesearch.select("ul");
//			for (int i = 0; i < uls.size(); i++) {
//				Element ul = uls.get(i);
//				Elements lis = ul.select("li");
//				for (int j = 0; j < lis.size(); j++) {
//					Element li = lis.get(j);
//					Elements as = li.select("a");
//					String record = as.get(as.size() - 1).text();
//					int idx = record.indexOf("(");
//					String name = record.substring(0, idx);
//
//					String numberCode = record.substring(idx + 1, record.length() - 1);
//					String code = (i == 0 ? "sh" : "sz") + numberCode;
//					String exchange = i == 0 ? "上海证券交易所" : "深圳证券交易所";
//					String type = i == 0 ? StockCodeUtils.anaylzSHPlate(numberCode) : StockCodeUtils.anaylzSZType(numberCode);
//
//					Stock stock = new Stock();
//					stock.setCode(code);
//					stock.setName(name);
//					stock.setExchange(exchange);
//					stock.setType(type);
//
//					stocks.add(stock);
//				}
//			}
//		} catch (Exception e) {
//			LOG.error("", e);
//		}
//		return stocks;
//	}
//
//}
