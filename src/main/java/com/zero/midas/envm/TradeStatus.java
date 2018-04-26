package com.zero.midas.envm;

public enum TradeStatus {

	NORMAL("正常交易");
	
	private String name;
	
	TradeStatus(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
