package com.zero.midas.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("StockDayData")
public class StockDayDataDO {

    @TableField("`code`")
    private String code;
    @TableField("`StatDate`")
    private String date;
    @TableField("`StartPrice`")
    private BigDecimal open;
    @TableField("`HighPrice`")
    private BigDecimal high;
    @TableField("`EndPrice`")
    private BigDecimal close;
    @TableField("`LowPrice`")
    private BigDecimal low;
    @TableField("`TotalHand`")
    private BigDecimal volumn;
    @TableField("`ChangePrice`")
    private BigDecimal priceChange;
    @TableField("`ChangeRatio`")
    private BigDecimal priceRatio;
    @TableField("`HandRate`")
    private BigDecimal turnover;
}
