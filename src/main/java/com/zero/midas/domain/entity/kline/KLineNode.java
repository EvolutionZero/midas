package com.zero.midas.domain.entity.kline;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author: fengzijian
 * @since: 2020/8/17 14:17
 * @Description:
 */
@Data
public class KLineNode {
    private String code;
    private LocalDate date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal close;
    private BigDecimal low;
    private BigDecimal priceRatio;

    public boolean isUp() {
        return close.compareTo(open) > 0;
    }

    public boolean isDown() {
        return open.compareTo(close) > 0;
    }

    public BigDecimal entityRatio() {
        BigDecimal ratio = new BigDecimal("1").subtract(upShadowRatio()).subtract(downShadowRatio());
        return ratio.compareTo(new BigDecimal("0")) > 0 ? ratio : new BigDecimal("0");
    }

    public BigDecimal upShadowRatio() {
        return high.equals(open) ? new BigDecimal("0") : high.subtract(open).abs().divide(high.subtract(low), 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal downShadowRatio() {
        return low.equals(close) ? new BigDecimal("0") : close.subtract(low).abs().divide(high.subtract(low), 2, BigDecimal.ROUND_HALF_UP);
    }

}
