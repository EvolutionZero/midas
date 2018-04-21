package com.zero.midas.workpackage;

import lombok.Getter;
import lombok.Setter;

import com.zero.midas.bean.bean.Company;
import com.zero.midas.bean.pojo.Stock;
import com.zero.midas.envm.WorkStatus;

public class IntelligenceCollectWp {

	@Getter
	@Setter
	private Stock stock;
	
	@Getter
	@Setter
	private WorkStatus collectIndustryStatus = WorkStatus.READY;
	
	@Getter
	@Setter
	private WorkStatus collectCompanyStatus = WorkStatus.READY;
	
	@Getter
	@Setter
	private String industry;
	
	@Getter
	@Setter
	private Company company;
	
	
}
