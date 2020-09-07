package com.zero.midas.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("StockBaseInfo")
public class StockBaseInfoDO {

    @TableField("`Code`")
    private String code;

    @TableField("`Name`")
    private String name;
    @TableField("`Exchange`")
    private String exchange;

}
