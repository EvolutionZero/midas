package com.zero.midas.app;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.midas.bean.bean.Company;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.unit.collect.StockBaseInfoCollector;
import com.zero.midas.unit.collect.StockCompanyCollector;
import com.zero.midas.unit.collect.StockIndustryCollector;
import com.zero.midas.unit.storage.StockStorage;

/**
 * 
 * <PRE>
 * 中二的情报收集
 * </PRE>
 * <B>项    目：</B> 
 * @version   1.0 2018年4月21日
 */
public class IntelligenceCollect {

	private static final Logger LOG = LoggerFactory.getLogger(IntelligenceCollect.class);
	
	public void exec(){
		StockStorage stockStorage = new StockStorage();
		StockBaseInfoCollector stockBaseInfoCollector = new StockBaseInfoCollector();
		List<Stock> stockBaseInfo = stockBaseInfoCollector.exec();
		stockStorage.saveOrUpdate(stockBaseInfo);
		
		List<Stock> tradableStock = stockStorage.queryTradableStock();
		int threadSize = 4;
		ExecutorService mainThreadPool = Executors.newFixedThreadPool(threadSize);
//		ExecutorService subThreadPool = Executors.newFixedThreadPool( 2 * threadSize);
		LOG.info("开始采集情报");
		for (Stock stock : tradableStock) {
			mainThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					LOG.info("收集{}情报！", stock.getCode());
					FutureTask<String> industryFuture = new FutureTask<String>(new StockIndustryCollector(stock.getCode()));
					FutureTask<Company> companyFuture = new FutureTask<Company>(new StockCompanyCollector(stock.getCode()));
					
//					Future<String> industryFuture = (Future<String>) subThreadPool.submit(new FutureTask<String>(new StockIndustryCollector(stock.getCode())));
//					Future<Company> companyFuture = (Future<Company>) subThreadPool.submit(new FutureTask<Company>(new StockCompanyCollector(stock.getCode())));
					new Thread(industryFuture).start();
					new Thread(companyFuture).start();
					try {
						while(true){
							if(industryFuture.isDone() && companyFuture.isDone()){
								String industry = industryFuture.get();
								Company company = companyFuture.get();
								stock.setIndustry(industry);
								stock.setBusiness(company.getBusiness());
								stock.setLocation(company.getLocation());
								stock.setListDate(company.getListDate() == null || "".equals(company.getListDate())  ? null : new Date(new SimpleDateFormat("yyyy-MM-dd").parse(company.getListDate()).getTime()));
								stock.setLocation(company.getLocation());
								stock.setConcept(company.getConcept());
								stock.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
								
								stockStorage.saveOrUpdate(stock);
								break;
							} else {
								Thread.sleep(100);
							}
							
						}
					} catch (InterruptedException e) {
						LOG.error("", e);
					} catch (ExecutionException e) {
						LOG.error("", e);
					} catch (ParseException e) {
						LOG.error("", e);
					} catch (Exception e) {
						LOG.error("", e);
					}
					
				}
			});
		}
		
	}
	
}


