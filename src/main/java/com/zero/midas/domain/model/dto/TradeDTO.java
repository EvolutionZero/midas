package com.zero.midas.domain.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TradeDTO {
    private String code;
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal high;
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
}
