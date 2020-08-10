//package com.zero.midas.app;
//
//import java.sql.Date;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.zero.midas.bean.pojo.Daily;
//import com.zero.midas.bean.pojo.Extreme;
//import com.zero.midas.bean.pojo.Stock;
//import com.zero.midas.envm.ExtremeLevel;
//import com.zero.midas.envm.ExtremeType;
//import com.zero.midas.unit.storage.DailyStorage;
//import com.zero.midas.unit.storage.ExtremeStorage;
//import com.zero.midas.unit.storage.StockStorage;
//
//public class FindExtreme {
//
//	private static final Logger LOG = LoggerFactory.getLogger(FindExtreme.class);
//
//	public void exec() {
//		StockStorage stockStorage = new StockStorage();
//		DailyStorage dailyStorage = new DailyStorage();
//		ExtremeStorage extremeStorage = new ExtremeStorage();
//		List<Stock> queryTradableStock = stockStorage.queryTradableStock();
//		for (Stock stock : queryTradableStock) {
//			List<Daily> line = dailyStorage.queryByCode(stock.getCode());
//
//			List<Extreme> shortExtremes = findExtremes(line);
//			List<Extreme> midExtremes = findExtremes(shortExtremes, ExtremeLevel.MIDDLE);
//			List<Extreme> longExtremes = findExtremes(midExtremes, ExtremeLevel.LONG);
//
//			List<Extreme> saveExtreme = new LinkedList<>();
//			saveExtreme.addAll(filte(shortExtremes, extremeStorage.queryLastDate(stock.getCode(), ExtremeLevel.SHORT)));
//			saveExtreme.addAll(filte(midExtremes, extremeStorage.queryLastDate(stock.getCode(), ExtremeLevel.MIDDLE)));
//			saveExtreme.addAll(filte(longExtremes, extremeStorage.queryLastDate(stock.getCode(), ExtremeLevel.LONG)));
//
//			extremeStorage.saveUnique(saveExtreme);
//		}
//	}
//
//	private List<Extreme> filte(List<Extreme> line, Date date){
//		if(date == null){
//			return line;
//		}
//		List<Extreme> result = new LinkedList<>();
//		for (Extreme extreme : line) {
//			if(extreme.getDate() != null && extreme.getDate().after(date)){
//				result.add(extreme);
//			}
//		}
//		return result;
//	}
//
//
////	private static void findHistoryExtreme() {
////		findAreaExtreme(Integer.MAX_VALUE);
////	}
//
////	public static void findAreaExtreme(int lastDay) {
////		LOG.info("开始寻找极值！");
////		List<String> codes = CodeDao.queryAllCodeOnExchange();
////		for (String code : codes) {
////			LOG.info("寻找{}的极值！", code);
////			long start = System.currentTimeMillis();
////			List<Stock> stocks = DailyDao.getStocks(code, lastDay);
////
////			List<Extreme> shortExtremes = findExtremes(stocks);
////			List<Extreme> midExtremes = findExtremes(shortExtremes, Extreme.MIDDLE);
////			List<Extreme> longExtremes = findExtremes(midExtremes, Extreme.LONG);
////
////			String shortLastDay = ExtremeDao.getLastDate(code, Extreme.SHORT);
////			shortExtremes = "".equals(shortLastDay) ? shortExtremes : after(shortLastDay, shortExtremes);
////
////			String middleLastDay = ExtremeDao.getLastDate(code, Extreme.MIDDLE);
////			midExtremes = "".equals(middleLastDay) ? midExtremes : after(middleLastDay, midExtremes);
////
////			String longLastDay = ExtremeDao.getLastDate(code, Extreme.MIDDLE);
////			longExtremes = "".equals(longLastDay) ? longExtremes : after(longLastDay, longExtremes);
////
////			ExtremeDao.save(shortExtremes);
////			ExtremeDao.save(midExtremes);
////			ExtremeDao.save(longExtremes);
////			long end = System.currentTimeMillis();
////			LOG.info("耗时 {}毫秒！", end - start);
////		}
////		LOG.info("结束寻找极值！");
////	}
//
////	public static List<Extreme> after(String standard, List<Extreme> extremes){
////		List<Extreme> result = new LinkedList<Extreme>();
////		if(!"".equals(standard)){
////			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////			for (Extreme extreme : extremes) {
////				try {
////					if(sdf.parse(extreme.getDate()).after(sdf.parse(standard))){
////						result.add(extreme);
////					}
////				} catch (ParseException e) {
////					LOG.error("", e);
////				}
////			}
////		}
////		return result;
////	}
//
//	private static List<Extreme> findExtremes(List<Daily> dailys){
//		List<Extreme> extremes = new LinkedList<Extreme>();
//		if(dailys.size() >= 3){
//			int len = dailys.size() - 1;
//			for (int i = 1; i < len; i++) {
//				Daily left = dailys.get(i - 1);
//				Daily mid = dailys.get(i);
//				Daily right = dailys.get(i + 1);
//
//				Extreme extreme = genExtreme(left, mid, right);
//				if(extreme != null){
//					extremes.add(extreme);
//				}
//			}
//
//		}
//		return extremes;
//	}
//
//	private static Extreme genExtreme(Daily left, Daily mid, Daily right){
//		Extreme extreme = null;
//		if(mid.getHigh() > left.getHigh() && mid.getHigh() > right.getHigh()){
//			extreme = new Extreme();
//			extreme.setCode(mid.getCode());
//			extreme.setDate(mid.getDate());
//			extreme.setValue(mid.getHigh());
//			extreme.setLevel(ExtremeLevel.SHORT.getName());
//			extreme.setType(ExtremeType.TOP.getName());
//
//		} else if(mid.getLow() < left.getLow() && mid.getLow() < right.getLow()){
//			extreme = new Extreme();
//			extreme.setCode(mid.getCode());
//			extreme.setDate(mid.getDate());
//			extreme.setValue(mid.getLow());
//			extreme.setLevel(ExtremeLevel.SHORT.getName());
//			extreme.setType(ExtremeType.BOTTOM.getName());
//		}
//		return extreme;
//	}
//
//	private static List<Extreme> findExtremes(List<Extreme> extremes, ExtremeLevel level){
//		List<Extreme> result = new LinkedList<Extreme>();
//		if(extremes.size() >= 3){
//			int len = extremes.size() - 1;
//			for (int i = 1; i < len; i++) {
//				Extreme left = extremes.get(i - 1);
//				Extreme mid = extremes.get(i);
//				Extreme right = extremes.get(i + 1);
//
//				Extreme extreme = genExtreme(left, mid, right, level);
//				if(extreme != null){
//					result.add(extreme);
//				}
//			}
//
//		}
//		return result;
//	}
//
//	private static Extreme genExtreme(Extreme left, Extreme mid, Extreme right, ExtremeLevel level){
//		Extreme extreme = null;
//		if(mid.getValue() > left.getValue() && mid.getValue() > right.getValue()){
//			extreme = new Extreme();
//			extreme.setCode(mid.getCode());
//			extreme.setDate(mid.getDate());
//			extreme.setValue(mid.getValue());
//			extreme.setLevel(level.getName());
//			extreme.setType(ExtremeType.TOP.getName());
//
//		} else if(mid.getValue() < left.getValue() && mid.getValue() < right.getValue()){
//			extreme = new Extreme();
//			extreme.setCode(mid.getCode());
//			extreme.setDate(mid.getDate());
//			extreme.setValue(mid.getValue());
//			extreme.setLevel(level.getName());
//			extreme.setType(ExtremeType.BOTTOM.getName());
//		}
//		return extreme;
//	}
//}
