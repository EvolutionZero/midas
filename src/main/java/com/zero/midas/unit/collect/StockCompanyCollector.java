package com.zero.midas.unit.collect;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.bean.bean.Company;
import com.zero.midas.utils.HttpUtils;

public class StockCompanyCollector implements Runnable, Callable<Company>{

	private static final Logger LOG = LoggerFactory.getLogger(StockCompanyCollector.class);
	
	private String code;
	
	public StockCompanyCollector(String code) {
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
	public Company call() throws Exception {
		Company company = new Company();
		String numberCode = code.replace("sh", "").replace("sz", "");
		String url = "http://stockpage.10jqka.com.cn/" + numberCode + "/";
		String html = null;
		try {
			html = HttpUtils.getText(url,"UTF-8");
			Document doc = Jsoup.parse(html);
			if(doc.select(".company_details").isEmpty()){
				return company;
			}
			Elements logos = doc.select(".m_logo");
			String name = null;
			if(!logos.isEmpty()){
				Element element = logos.get(0);
				name  = element.select("a").get(0).select("strong").text();
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
				if(i == 1 || i == 3){
					value = dds.get(i).attr("title").trim();
				}
				values.add(value);
			}
			values.remove(2);
			for (int i = 0; i < keys.size(); i++) {
				map.put(keys.get(i).replace("：", ""), values.get(i));
			}
			
			String concept = map.get("涉及概念").replace("，", ",");
			String listDate = map.get("上市日期");
			String location = map.get("所属地域");
			String business = map.get("主营业务").trim().replace("　　", "").replace("；", ";");
			
			company.setName(name);
			company.setConcept(concept);
			company.setListDate(listDate);
			company.setLocation(location);
			company.setBusiness(business);
			
		} catch (Exception e) {
			LOG.error("", e);
		}
		return company;
	}

}
