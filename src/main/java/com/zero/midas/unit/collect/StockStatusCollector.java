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
import com.zero.midas.utils.HttpUtils;

public class StockStatusCollector implements Runnable, Callable<String>{

	private static final Logger LOG = LoggerFactory.getLogger(StockStatusCollector.class);
	
	private String code;
	
	public StockStatusCollector() {
	}
	
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
	
	@Override
	public String call() throws Exception {
		return exec(code);
	}

	public String exec(String code) {
		String url = "http://quote.eastmoney.com/" + code + ".html";
		String status = "停牌";
		try {
			String html = HttpUtils.getText(url,"GBK");
			
			int startIdx = html.indexOf("var Def = new QaDefault(");
			html = html.substring(startIdx);
			int endIdx = html.indexOf("\n");
			html = html.substring(0, endIdx);
			
			html = html.substring(html.indexOf("(") + 1, html.indexOf(")"));
			String[] params = html.split(",");
			String param = params.length > 10 ? params[10] : "";
			param = param.trim().replace("'", "");
			
			if("0".equals(param)){
				status = "未上市";
				
			} else if("2".equals(param)){
				status = "已退市";
				
			} else if("3".equals(param)){
				status = "暂停上市";
				
			} else if("4".equals(param)){
				status = "终止上市";
			}
			
//			if(status == null){
//				WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
//				webClient.getOptions().setJavaScriptEnabled(true);
//				webClient.getOptions().setCssEnabled(true);
//				
//				//获取页面
//				HtmlPage page = webClient.getPage(url);
//			    Document doc = Jsoup.parse(page.asXml());
//			 
//				Elements elements = doc.select("#arrowud");
//				if(!elements.isEmpty()){
//					Elements a = elements.select("strong span a");
//					status = a.isEmpty() ? null : a.get(0).text().trim();
//				}
//				
//			}
		} catch (Exception e) {
			LOG.error("采集[" + code + "]发生异常", e);
		}
		return status;
	}

	public static void main(String[] args) {
		try {
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			
			//获取页面
			String url = "http://quote.eastmoney.com/sh600051.html";
			HtmlPage page = webClient.getPage(url);
			System.out.println(page.asXml());
//			 Document doc = Jsoup.parse(html);
////			 
//				Elements elements = doc.select("#arrowud");
//				if(!elements.isEmpty()){
//					Elements a = elements.select("strong span a");
//					String status = a.isEmpty() ? null : a.get(0).text().trim();
//					System.out.println(status);
//				}
//			String status = new StockStatusCollector("sh600001").call();
//			System.out.println(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
