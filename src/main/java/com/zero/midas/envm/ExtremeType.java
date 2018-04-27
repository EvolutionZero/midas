package com.zero.midas.envm;

public enum ExtremeType {

	TOP("高点"),BOTTOM("低点");
	
	private String name;
	
	ExtremeType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
