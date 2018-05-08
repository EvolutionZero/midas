package com.zero.midas.bean.pojo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Trend {

	@Getter
	@Setter
	private String code;
	
	@Getter
	@Setter
	private String type;
	
	@Getter
	@Setter
	private Date start;
	
	@Getter
	@Setter
	private Date end;
	
	@Getter
	@Setter
	private double range;
	
}
