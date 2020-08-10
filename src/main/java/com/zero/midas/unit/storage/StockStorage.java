//package com.zero.midas.unit.storage;
//
//import java.sql.Date;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.zero.midas.bean.pojo.Stock;
//import com.zero.midas.envm.TradeStatus;
//
//public class StockStorage extends FinanceBaseStorage<Stock>{
//
//	public List<Stock> queryTradableStock(){
//		Object[] params = new Object[]{TradeStatus.NORMAL.getName(), "上证A股", "上证B股", "深证A股", "深证A股A2权证", "深证A股增发", "深证A股A1权证","深证A股转配"
//										, "深证B股", "深证B股增发", "深证B股权证", "创业板证券", "创业板增发", "创业板权证"};
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < params.length - 1; i++) {
//			sb.append("?,");
//		}
//		if(sb.length() > 1){
//			sb.delete(sb.length() - 1, sb.length());
//		}
//		String sql = query + " where status = ? AND type IN (" + sb.toString() + ")";
//		return query(sql, params);
//	}
//
//	public List<Stock> queryCollectInfoStock(){
//		String sql = query + " where type like '%A股%' OR  type like '%B股%' OR  type like '%创业板%'";
//		return query(sql);
//	}
//
//	public List<Stock> queryAbnormalTradeStatusStock(){
//
//		String queryMaxDate = "select max(date) maxDate from t_daily  d"
//				+ " join (select `code` from t_stock where `status` = ? limit 10) s on d.`code` = s.`code`";
//		List<Map<String, Object>> maxDateResult = queryForList(queryMaxDate, new Object[]{TradeStatus.NORMAL.getName()});
//
//		Date maxDate = null;
//		if(maxDateResult.isEmpty()){
//			return new LinkedList<>();
//		} else {
//			maxDate = (Date)maxDateResult.get(0).get("maxDate");
//		}
//
//		String sql = query + " where `code` in ("
//				+ "select `code` from (select `code`,max(date) lastDate from  t_daily group by `code`) a "
//				+ "where a.lastDate < date_format(?,'%y-%m-%d')"
//				+ " union on "
//				+ " select `code` from t_stock where status is null"
//				+ ")";
//		return query(sql, new Object[]{maxDate});
//	}
//
//	public void updateNormalStock(){
//
//		String queryMaxDate = "select max(date) maxDate from t_daily  d"
//				+ " join (select `code` from t_stock where `status` = ? limit 10) s on d.`code` = s.`code`";
//		List<Map<String, Object>> maxDateResult = queryForList(queryMaxDate, new Object[]{TradeStatus.NORMAL.getName()});
//
//		Date maxDate = null;
//		if(maxDateResult.isEmpty()){
//			return ;
//		} else {
//			maxDate = (Date)maxDateResult.get(0).get("maxDate");
//		}
//
//		String sql = "update t_stock set STATUS = ? where `code` in ("
//				+ "select `code` from (select `code`,max(date) lastDate from  t_daily group by `code`) a "
//				+ "where a.lastDate = date_format(?,'%y-%m-%d')"
//				+ ")";
//		update(sql, new Object[]{TradeStatus.NORMAL.getName(), maxDate});
//	}
//
//	public List<Stock> queryByKeywords(String keyword){
//		String[] array = keyword.trim().split(" ");
//		Set<String> keywords = new HashSet<>();
//		for (String temp : array) {
//			if(!"".equals(temp.trim())){
//				keywords.add(temp);
//			}
//		}
//		return queryByKeywords(keywords);
//	}
//
//	public List<Stock> queryByKeywords(Collection<String> keywords){
//		if(keywords == null || keywords.isEmpty()){
//			return new LinkedList<Stock>();
//		}
//		List<Object> paramList = new LinkedList<Object>();
//
//		StringBuilder sql = new StringBuilder(query).append(" WHERE ( type like '%A股%' OR  type like '%B股%' OR  type like '%创业板%' ) AND ");
//		for (String keyword : keywords) {
//			sql.append(" ( industry like CONCAT('%', ?, '%') OR");
//			sql.append(" concept like CONCAT('%', ?, '%') OR");
//			sql.append(" location like CONCAT('%', ?, '%') OR");
//			sql.append(" business like CONCAT('%', ?, '%') )");
//			sql.append(" AND ");
//
//			paramList.add(keyword);
//			paramList.add(keyword);
//			paramList.add(keyword);
//			paramList.add(keyword);
//		}
//		sql.delete(sql.length() - " AND ".length(), sql.length());
//		return query(sql.toString(), toArray(paramList));
//	}
//
//	public int updateStatusByCode(String status, String code){
//		String sql = "UPDATE T_STOCK SET STATUS = ? WHERE `CODE` = ?";
//		return update(sql, new Object[]{status, code});
//	}
//
//
//	public List<Stock> queryAllConcept(){
//		String sql = query + " WHERE CONCEPT IS NOT NULL";
//		return query(sql);
//	}
//}
