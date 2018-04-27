package com.zero.midas.envm;

public enum ExtremeLevel {

	SHORT("短期"), MIDDLE("中期"), LONG("长期");

	private String name;

	ExtremeLevel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
