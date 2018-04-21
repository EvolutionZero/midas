package com.zero.midas.bean.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Company {

	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String listDate;
	
	@Getter
	@Setter
	private String location;
	
	@Getter
	@Setter
	private String business;
	
	@Getter
	@Setter
	private String concept;
}
