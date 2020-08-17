package com.zero.midas.domain.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author: fengzijian
 * @since: 2020/8/17 14:17
 * @Description:
 */
@Data
public class KLineDTO {
    private String code;
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal close;
    private BigDecimal low;
}
