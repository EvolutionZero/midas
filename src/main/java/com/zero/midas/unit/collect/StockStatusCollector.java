package com.zero.midas.unit.collect;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class StockStatusCollector implements Runnable, Callable<String>{

	private static final Logger LOG = LoggerFactory.getLogger(StockStatusCollector.class);
	
	private String code;
	
	public StockStatusCollector(String code) {
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
	
	@SuppressWarnings("resource")
	@Override
	public String call() throws Exception {
		String url = "http://quote.eastmoney.com/" + code + ".html";
		String status = null;
		try {
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			
			//获取页面
			HtmlPage page = webClient.getPage(url);
		    Document doc = Jsoup.parse(page.asXml());
		 
			Elements elements = doc.select("#arrowud");
			if(!elements.isEmpty()){
				Elements a = elements.select("strong span a");
				status = a.isEmpty() ? null : a.get(0).text().trim();
			}
		} catch (Exception e) {
			LOG.error("", e);
		}
		return status;
	}

	public static void main(String[] args) {
		try {
			String html = FileUtils.readFileToString(new File("./status.html"), "UTF-8");
			 Document doc = Jsoup.parse(html);
//			 
				Elements elements = doc.select("#arrowud");
				if(!elements.isEmpty()){
					Elements a = elements.select("strong span a");
					String status = a.isEmpty() ? null : a.get(0).text().trim();
					System.out.println(status);
				}
//			new StockStatusCollector("sh600880").call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
