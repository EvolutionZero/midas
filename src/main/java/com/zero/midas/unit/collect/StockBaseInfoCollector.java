package com.zero.midas.unit.collect;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.storage.StockStorage;
import com.zero.midas.utils.HttpUtils;

public class StockBaseInfoCollector {

	private static final Logger LOG = LoggerFactory.getLogger(StockBaseInfoCollector.class);
	
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream(new File(System.getProperty("user.dir") + "/conf/proxool.properties"));
			Properties properties = new Properties();
			properties.load(is);
			PropertyConfigurator.configure(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StockBaseInfoCollector stockBaseInfoCollector = new StockBaseInfoCollector();
		List<Stock> stocks = stockBaseInfoCollector.exec();
		StockStorage stockStorage = new StockStorage();
		for (Stock stock : stocks) {
			stockBaseInfoCollector.modifyIndustry(stock);
			stockStorage.saveOrUpdate(stock);
		}
//		new StockStorage().saveOrUpdate(stocks);
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
				String plate = i == 0 ? "上证" : "深证";
				
				
				
				Stock stock = new Stock();
				stock.setCode(code);
				stock.setName(name);
				stock.setPlate(plate);
				
				stocks.add(stock);
			}
		}
		return stocks;
	}
	
	private void modifyIndustry(Stock stock){
		System.out.println(stock.getCode());
		String numberCode = stock.getCode().replace("sh", "").replace("sz", "");
		if(numberCode.startsWith("60") || numberCode.startsWith("90") || numberCode.startsWith("00")
				 || numberCode.startsWith("20") || numberCode.startsWith("30")){
			
		} else {
			return ;
		}
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
			return ;
		}
	}
}
