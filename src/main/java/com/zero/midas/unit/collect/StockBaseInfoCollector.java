package com.zero.midas.unit.collect;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.Initialization;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.storage.StockStorage;
import com.zero.midas.utils.HttpUtils;
import com.zero.midas.utils.StockCodeUtils;

public class StockBaseInfoCollector {

	private static final Logger LOG = LoggerFactory.getLogger(StockBaseInfoCollector.class);
	
	public static void main(String[] args) {
		Initialization.exec();
		StockBaseInfoCollector stockBaseInfoCollector = new StockBaseInfoCollector();
		List<Stock> stocks = stockBaseInfoCollector.exec();
		StockStorage stockStorage = new StockStorage();
		stockStorage.saveOrUpdate(stocks);
		
//		List<Stock> tradableStock = stockStorage.queryTradableStock();
//		for (Stock stock : tradableStock) {
//			stockBaseInfoCollector.modifyIndustry(stock);
//			stockBaseInfoCollector.modifyBusiness(stock);
//			stockStorage.saveOrUpdate(stock);
//		}
//		
//		for (Stock stock : stocks) {
//			String numberCode = stock.getCode().replace("sh", "").replace("sz", "");
//			if(numberCode.startsWith("60") || numberCode.startsWith("90") || numberCode.startsWith("00")
//					 || numberCode.startsWith("20") || numberCode.startsWith("30")){
//				
//			} else {
//				continue;
//			}
//			stockBaseInfoCollector.modifyIndustry(stock);
//			stockBaseInfoCollector.modifyBusiness(stock);
//			stockStorage.saveOrUpdate(stock);
//		}
	}
	
	public List<Stock> exec(){
		String html = HttpUtils.getText("http://quote.eastmoney.com/stocklist.html", "GBK");
		Document doc = Jsoup.parse(html);
		Elements quotesearch = doc.select("#quotesearch");
		Elements uls = quotesearch.select("ul");
		List<Stock> stocks = new LinkedList<>();
		for (int i = 0; i < uls.size(); i++) {
			Element ul = uls.get(i);
			Elements lis = ul.select("li");
			for (int j = 0; j < lis.size(); j++) {
				Element li = lis.get(j);
				Elements as = li.select("a");
				String record = as.get(as.size() - 1).text();
				int idx = record.indexOf("(");
				String name = record.substring(0, idx);
				
				String numberCode = record.substring(idx + 1, record.length() - 1);
				String code = (i == 0 ? "sh" : "sz") + numberCode;
				String exchange = i == 0 ? "上海证券交易所" : "深圳证券交易所";
				String type = i == 0 ? StockCodeUtils.anaylzSHPlate(numberCode) : StockCodeUtils.anaylzSZType(numberCode);
				
				Stock stock = new Stock();
				stock.setCode(code);
				stock.setName(name);
				stock.setExchange(exchange);
				stock.setType(type);
				
				stocks.add(stock);
			}
		}
		return stocks;
	}
	
	private void modifyIndustry(Stock stock){
		String url = "http://finance.ifeng.com/app/hq/stock/" + stock.getCode();
		String html = HttpUtils.getText(url,"UTF-8");
		Document doc = Jsoup.parse(html);
		try {
			html = HttpUtils.getText(url, "UTF-8");
			doc = Jsoup.parse(html);
			Elements elements = doc.select(".lastBot");
			Elements spans = elements.select("span");
			if(spans.isEmpty()){
				return ;
			}
			String industry = spans.get(0).ownText();
			industry = industry.replace("所属行业：", "").replace(" ", "").trim().replace("　　", "");
			System.out.println(industry);
			stock.setIndustry(industry);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
	
	private void modifyBusiness(Stock stock){
		String numberCode = stock.getCode().replace("sh", "").replace("sz", "");
		String url = "http://stockpage.10jqka.com.cn/" + numberCode + "/";
		String html = null;
		try {
			html = HttpUtils.getText(url,"UTF-8");
			Document doc = Jsoup.parse(html);
			if(doc.select(".company_details").isEmpty()){
				return ;
			}
			Element companyDetails = doc.select(".company_details").get(0);
			Map<String, String> map = new HashMap<String, String>();
			Elements dts = companyDetails.select("dt");
			Elements dds = companyDetails.select("dd");
			List<String> keys = new LinkedList<String>();
			List<String> values = new LinkedList<String>();
			for (int i = 0; i < dts.size(); i++) {
				String key = dts.get(i).ownText();
				keys.add(key);
			}
			for (int i = 0; i < dds.size(); i++) {
				String value = dds.get(i).ownText();
				if(i == 3){
					value = dds.get(i).attr("title").trim();
				}
				values.add(value);
			}
			values.remove(2);
			for (int i = 0; i < keys.size(); i++) {
				map.put(keys.get(i).replace("：", ""), values.get(i));
			}
			String listDate = map.get("上市日期");
			String location = map.get("所属地域");
			String business = map.get("主营业务").trim().replace("　　", "");
			
			stock.setLocation(location);
			stock.setListDate(listDate);
			stock.setBusiness(business);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
}
