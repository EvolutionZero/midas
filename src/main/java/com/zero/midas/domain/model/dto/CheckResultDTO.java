package com.zero.midas.domain.model.dto;

import lombok.Data;

import java.math.BigDecimal;

import static com.zero.midas.utils.BigDecimalUtils.eq;
import static com.zero.midas.utils.BigDecimalUtils.gt;

/**
 * @author: fengzijian
 * @since: 2020/8/28 17:13
 * @Description:
 */
@Data
public class CheckResultDTO {

    /**
     * 周期数
     */
    private int cycle;

    /**
     * 百分比
     */
    private BigDecimal percent = new BigDecimal("0");

    public void addCycle() {
        this.cycle += 1;
    }

    public boolean correct(){
        return gt(percent, new BigDecimal("0"));
    }

    public boolean balance(){
        return eq(percent, new BigDecimal("0"));
    }

}
