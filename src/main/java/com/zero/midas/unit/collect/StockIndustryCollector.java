package com.zero.midas.unit.collect;

import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.utils.HttpUtils;

public class StockIndustryCollector implements Runnable, Callable<String>{

	private static final Logger LOG = LoggerFactory.getLogger(StockIndustryCollector.class);
	
	private String code;
	
	public StockIndustryCollector(String code) {
		super();
		this.code = code;
	}
	
	@Override
	public void run() {
		try {
			call();
		} catch (Exception e) {
			LOG.error("", e);
		}
		
	}
	
	@Override
	public String call() throws Exception {
		String url = "http://finance.ifeng.com/app/hq/stock/" + code;
		String html = HttpUtils.getText(url,"UTF-8");
		String industry = null;
		Document doc = Jsoup.parse(html);
		try {
			html = HttpUtils.getText(url, "UTF-8");
			doc = Jsoup.parse(html);
			Elements elements = doc.select(".lastBot");
			Elements spans = elements.select("span");
			if(spans.isEmpty()){
				return industry;
			}
			industry = spans.get(0).ownText().replace("所属行业：", "").replace(" ", "").trim().replace("　　", "");
		} catch (Exception e) {
			LOG.error("", e);
		}
		return industry;
	}

}
