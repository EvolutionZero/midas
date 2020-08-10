//package com.zero.midas.app;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.zero.midas.bean.pojo.Stock;
//import com.zero.midas.unit.collect.StockStatusCollector;
//import com.zero.midas.unit.storage.StockStorage;
//
///**
// *
// * <PRE>
// * 中二的情报收集
// * </PRE>
// * <B>项    目：</B>
// * @version   1.0 2018年4月21日
// */
//public class ExceptionTrandeStatusCollect {
//
//	private static final Logger LOG = LoggerFactory.getLogger(ExceptionTrandeStatusCollect.class);
//
//	public void exec(){
//		StockStorage stockStorage = new StockStorage();
//		stockStorage.updateNormalStock();
//		List<Stock> abnormalTradeStatusStock = stockStorage.queryAbnormalTradeStatusStock();
//		LOG.info("开始采集不正常交易状态");
//		for (Stock stock : abnormalTradeStatusStock) {
//			LOG.info("收集{}不正常交易状态！", stock.getCode());
//			try {
//				String status = new StockStatusCollector().exec(stock.getCode());
//				stockStorage.updateStatusByCode(status, stock.getCode());
//			} catch (Exception e) {
//				LOG.error("", e);
//			}
//		}
//
//	}
//
//}
