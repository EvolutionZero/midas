//package com.zero.midas.bean.pojo;
//
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.Column;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.zero.orm.core.PojoBaseBean;
//
//@ToString
//@Table(name="T_DAILY")
//public class Daily extends PojoBaseBean{
//
//	private static final Logger LOG = LoggerFactory.getLogger(Daily.class);
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="code")
//	private String code;
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="date")
//	private Date date;
//
//	@Getter
//	@Setter
//	@Column(name="open")
//	private Double open;
//
//	@Getter
//	@Setter
//	@Column(name="high")
//	private Double high;
//
//	@Getter
//	@Setter
//	@Column(name="close")
//	private Double close;
//
//	@Getter
//	@Setter
//	@Column(name="low")
//	private Double low;
//
//	@Getter
//	@Setter
//	@Column(name="change")
//	private Double change;
//
//	@Getter
//	@Setter
//	@Column(name="valueChange")
//	private Double valueChange;
//
//	@Getter
//	@Setter
//	@Column(name="ratioChange")
//	private Double ratioChange;
//
//	@Getter
//	@Setter
//	@Column(name="ma5")
//	private Double ma5;
//
//	@Getter
//	@Setter
//	@Column(name="ma10")
//	private Double ma10;
//
//	@Getter
//	@Setter
//	@Column(name="ma20")
//	private Double ma20;
//
//	@Getter
//	@Setter
//	@Column(name="ma30")
//	private Double ma30;
//
//	@Getter
//	@Setter
//	@Column(name="vma5")
//	private Double vma5;
//
//	@Getter
//	@Setter
//	@Column(name="vma10")
//	private Double vma10;
//
//	@Getter
//	@Setter
//	@Column(name="vma20")
//	private Double vma20;
//
//	@Getter
//	@Setter
//	@Column(name="turnover")
//	private Double turnover;
//
//
//	@Getter
//	@Setter
//	@Column(name="upper")
//	private Double upper;
//
//	@Getter
//	@Setter
//	@Column(name="middle")
//	private Double middle;
//
//	@Getter
//	@Setter
//	@Column(name="lower")
//	private Double lower;
//
//	@Getter
//	@Setter
//	@Column(name="percentB")
//	private Double percentB;
//
//	@Getter
//	@Setter
//	@Column(name="wr5")
//	private Double wr5;
//
//	@Getter
//	@Setter
//	@Column(name="wr10")
//	private Double wr10;
//
//	@Getter
//	@Setter
//	@Column(name="wr20")
//	private Double wr20;
//
//	@Getter
//	@Setter
//	@Column(name="wr30")
//	private Double wr30;
//
//	@Getter
//	@Setter
//	@Column(name="updateTime" , columnDefinition="timestamp default CURRENT_TIMESTAMP")
//	private Timestamp updateTime;
//
//	@Getter
//	@Setter
//	@Column(name="insertTime" , updatable = false, columnDefinition="timestamp default CURRENT_TIMESTAMP")
//	private Timestamp insertTime;
//
//	public Daily() {
//	}
//
//	public Daily(Map<String, Object> resultSet) {
//		super(resultSet);
//	}
//
//
//	public Daily(String code, List<Object> data){
//
//			String date = (String)data.get(0);
//			String open = (String)data.get(1);
//			String high = (String)data.get(2);
//			String close = (String)data.get(3);
//			String low = (String)data.get(4);
//			String change = (String)data.get(5);
//			String valueChange = (String)data.get(6);
//			String ratioChange = (String)data.get(7);
//			String ma5 = (String)data.get(8);
//			String ma10 = (String)data.get(9);
//			String ma20 = (String)data.get(10);
//			String vma5 = (String)data.get(11);
//			String vma10 = (String)data.get(12);
//			String vma20 = (String)data.get(13);
//
//			String turnover = "-1";
//			if(data.size() > 14){
//				turnover = (String)data.get(14);
//
//			}
//			this.code = code;
//			try {
//				this.date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date.replace(",", "")).getTime());
//			} catch (ParseException e) {
//				LOG.error("", e);
//			}
//			this.open = Double.valueOf(open.replace(",", ""));
//			this.high = Double.valueOf(high.replace(",", ""));
//			this.close = Double.valueOf(close.replace(",", ""));
//			this.low = Double.valueOf(low.replace(",", ""));
//			this.change = Double.valueOf(change.replace(",", ""));
//			this.valueChange = Double.valueOf(valueChange.replace(",", ""));
//			this.ratioChange = Double.valueOf(ratioChange.replace(",", ""));
//			this.ma5 = Double.valueOf(ma5.replace(",", ""));
//			this.ma10 = Double.valueOf(ma10.replace(",", ""));
//			this.ma20 = Double.valueOf(ma20.replace(",", ""));
//			this.vma5 = Double.valueOf(vma5.replace(",", ""));
//			this.vma10 = Double.valueOf(vma10.replace(",", ""));
//			this.vma20 = Double.valueOf(vma20.replace(",", ""));
//			this.turnover = Double.valueOf(turnover.replace(",", ""));
//	}
//
//
//}
