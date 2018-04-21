package com.zero.midas.utils;

public class StockCodeUtils {

	public static String anaylzSHPlate(String numberCode){
		String plate = null;
		if(numberCode.startsWith("001")){
			plate = "国债现货";
		} else if(numberCode.startsWith("110") || numberCode.startsWith("120")){
			plate = "企业债券";
		} else if(numberCode.startsWith("129") || numberCode.startsWith("100")){
			plate = "可转换债券";
		} else if(numberCode.startsWith("201")){
			plate = "国债回购";
		} else if(numberCode.startsWith("310")){
			plate = "国债期货";
		} else if(numberCode.startsWith("500") || numberCode.startsWith("550")){
			plate = "基金";
		} else if(numberCode.startsWith("600") || numberCode.startsWith("601") || numberCode.startsWith("603")){
			plate = "上证A股";
		} else if(numberCode.startsWith("900")){
			plate = "上证B股";
		} else if(numberCode.startsWith("700")){
			plate = "配股";
		} else if(numberCode.startsWith("710")){
			plate = "转配股";
		} else if(numberCode.startsWith("701")){
			plate = "转配股再配股";
		} else if(numberCode.startsWith("711")){
			plate = "转配股再转配股";
		} else if(numberCode.startsWith("720")){
			plate = "红利";
		} else if(numberCode.startsWith("730")){
			plate = "新股申购";
		} else if(numberCode.startsWith("735")){
			plate = "新基金申购";
		} else if(numberCode.startsWith("737")){
			plate = "新股配售";
		}
		return plate;
	}
	
	
	public static String anaylzSZType(String numberCode){
		String plate = null;
		if(numberCode.startsWith("00")){
			plate = "深证A股";
		} else if(numberCode.startsWith("03")){
			plate = "深证A股A2权证";
		} else if(numberCode.startsWith("07")){
			plate = "深证A股增发";
		} else if(numberCode.startsWith("08")){
			plate = "深证A股A1权证";
		} else if(numberCode.startsWith("09")){
			plate = "深证A股转配";
		} else if(numberCode.startsWith("10")){
			plate = "国债现货";
		} else if(numberCode.startsWith("11")){
			plate = "债券";
		} else if(numberCode.startsWith("12")){
			plate = "可转换债券";
		} else if(numberCode.startsWith("13")){
			plate = "国债回购";
		} else if(numberCode.startsWith("17")){
			plate = "原有投资基金";
		} else if(numberCode.startsWith("18")){
			plate = "证券投资基金";
		} else if(numberCode.startsWith("20")){
			plate = "深证B股";
		} else if(numberCode.startsWith("27")){
			plate = "深证B股增发";
		} else if(numberCode.startsWith("28")){
			plate = "深证B股权证";
		} else if(numberCode.startsWith("30")){
			plate = "创业板证券";
		} else if(numberCode.startsWith("37")){
			plate = "创业板增发";
		} else if(numberCode.startsWith("38")){
			plate = "创业板权证";
		} else if(numberCode.startsWith("39")){
			plate = "综合指数/成份指数";
		}
		return plate;
	}
}
