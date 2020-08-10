//package com.zero.midas.bean.pojo;
//
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.Map;
//
//import javax.persistence.Column;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.zero.orm.core.PojoBaseBean;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@ToString
//@Table(name="T_EXTREME")
//public class Extreme extends PojoBaseBean{
//
//	public Extreme() {
//	}
//
//	public Extreme(Map<String, Object> resultSet) {
//		super(resultSet);
//	}
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="code")
//	private String code;
//
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="date")
//	private Date date;
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="level")
//	private String level;
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="type")
//	private String type;
//
//	@Getter
//	@Setter
//	@Id
//	@Column(name="value")
//	private double value;
//
//	@Getter
//	@Setter
//	@Column(name="insertTime" , updatable = false, columnDefinition="timestamp default CURRENT_TIMESTAMP")
//	private Timestamp insertTime;
//
//}
