package com.zero.midas.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_stock")
public class StockDO {
    @TableField("`code`")
    private String code;
    private String name;
    private String exchange;
    private String type;
    private String industry;
    private String concept;
    private String location;
    private LocalDate listDate;
    private String business;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;
}
