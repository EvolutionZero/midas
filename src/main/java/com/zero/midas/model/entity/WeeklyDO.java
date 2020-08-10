package com.zero.midas.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_weekly")
public class WeeklyDO {
    @TableField("`code`")
    private String code;
    private LocalDate date;
    @TableField("`open`")
    private BigDecimal open;
    private BigDecimal high;
    @TableField("`close`")
    private BigDecimal close;
    private BigDecimal low;
    private BigDecimal volumn;
    private BigDecimal priceChange;
    private BigDecimal priceRatio;
    private BigDecimal ma5;
    private BigDecimal ma10;
    private BigDecimal ma20;
    private BigDecimal vma5;
    private BigDecimal vma10;
    private BigDecimal vma20;
    private BigDecimal turnover;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

}
