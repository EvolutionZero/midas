package com.zero.midas.bean.pojo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.zero.orm.core.PojoBaseBean;

@ToString
@Table(name="T_STOCK")
public class Stock extends PojoBaseBean{

	public Stock() {
	}
	
	public Stock(Map<String, Object> resultSet) {
		super(resultSet);
	}
	
	@Getter
	@Setter
	@Id
	@Column(name="code")
	private String code;
	
	@Getter
	@Setter
	@Column(name="name")
	private String name;
	
	@Getter
	@Setter
	@Column(name="exchange")
	private String exchange;
	
	@Getter
	@Setter
	@Column(name="type")
	private String type;
	
	@Getter
	@Setter
	@Column(name="industry")
	private String industry;
	
	@Getter
	@Setter
	@Column(name="concept")
	private String concept;
	
	@Getter
	@Setter
	@Column(name="location")
	private String location;
	
	@Getter
	@Setter
	@Column(name="listDate")
	private Date listDate;
	
	@Getter
	@Setter
	@Column(name="business")
	private String business;
	
	@Getter
	@Setter
	@Column(name="updateTime", columnDefinition="timestamp default CURRENT_TIMESTAMP")
	private Timestamp updateTime;
	
	@Getter
	@Setter
	@Column(name="insertTime" , updatable = false, columnDefinition="timestamp default CURRENT_TIMESTAMP")
	private Timestamp insertTime;
}
