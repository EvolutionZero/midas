package com.zero.midas.bean.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * <PRE>
 * 布林带
 * </PRE>
 */
@ToString
public class Boll {

	@Getter
	@Setter
	private Double upper;
	
	@Getter
	@Setter
	private Double middle;
	
	@Getter
	@Setter
	private Double lower;
	
	@Getter
	@Setter
	private Double percentB;

}
