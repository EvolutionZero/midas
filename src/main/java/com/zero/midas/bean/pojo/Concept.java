package com.zero.midas.bean.pojo;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zero.orm.core.PojoBaseBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Table(name="t_concept")
public class Concept extends PojoBaseBean{

	@Getter
	@Setter
	@Id
	@Column(name="code")
	private String code;
	
	@Getter
	@Setter
	@Id
	@Column(name="concept")
	private String concept;
	
	@Getter
	@Setter
	@Column(name="status")
	private String status;
	
	@Getter
	@Setter
	@Column(name="insertTime" , updatable = false, columnDefinition="timestamp default CURRENT_TIMESTAMP")
	private Timestamp insertTime;
	
	public Concept() {
	}
	
	public Concept(Map<String, Object> resultSet) {
		super(resultSet);
	}
	
}
