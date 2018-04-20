package com.zero.midas.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	
	public static void main(String[] args) throws IOException {
		String html = HttpUtils.getText("http://quote.eastmoney.com/stocklist.html", "GBK");
		FileUtils.write(new File("./stock.html"), html);
		Document doc = Jsoup.parse(new String(html.getBytes(), "UTF-8"));
		Elements quotesearch = doc.select("#quotesearch");
		Elements uls = quotesearch.select("ul");
		for (int i = 0; i < uls.size(); i++) {
			Element ul = uls.get(i);
			Elements lis = ul.select("li");
			for (int j = 0; j < lis.size(); j++) {
				Element li = lis.get(j);
				Elements as = li.select("a");
				String record = as.get(as.size() - 1).text();
				int idx = record.indexOf("(");
				String name = record.substring(0, idx);
				String code = (i == 0 ? "sh" : "sz") + record.substring(idx + 1, record.length() - 1);
				System.out.println(name + ":" + code);
			}
		}
		String url = "http://finance.ifeng.com/app/hq/stock/sh600000";
		try {
			html = HttpUtils.getText(url, "UTF-8");
			doc = Jsoup.parse(html);
			Elements elements = doc.select(".lastBot");
			Elements spans = elements.select("span");
			String industry = spans.get(0).ownText();
			industry = industry.replace("所属行业：", "").replace(" ", "").trim().replace("　　", "");
			System.out.println(industry);
			Elements as = spans.get(1).select("a");
			
			int size = as.size();
			for (int i = 0; i < size; i++) {
				org.jsoup.nodes.Element element = as.get(i);
				String ownText = element.ownText();
				System.out.println(ownText);
			}
		} catch (Exception e) {
			LOG.error("", e);
			return ;
		}
	}
	
	
	public static String getText(String urlStr, String charset) {
		HttpClient client = new HttpClient();
//		client.setTimeout( 5 * 1000);
		HttpMethod method = new GetMethod(urlStr);
		String response = "";
		try {
			client.executeMethod(method);
			byte[] responseBody = null;
			responseBody = method.getResponseBody();
			response = new String(responseBody, charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);

		}
		return response;
	}

	public static String getCharset(HttpMethod method) {
		String charset = "utf-8";
		Header contentType = method.getResponseHeader("Content-Type");
		Header[] responseHeaders = method.getResponseHeaders();
		String value = contentType.getValue();
		String[] attrs = value.split(";");
		for (String attr : attrs) {
			if (attr.contains("charset=")) {
				charset = attr.trim().replace("charset=", "");
			}
		}
		return charset;
	}

}
