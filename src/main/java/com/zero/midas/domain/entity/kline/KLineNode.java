package com.zero.midas.domain.entity.kline;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.eq;
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
    private BigDecimal volumn;
    public boolean isUp() {
        return close.compareTo(open) > 0;
    }

    public boolean isDown() {
        return open.compareTo(close) > 0;
    }

    /**
     * 当前周期变化百分比, 本周期开盘价和收盘价的升降比
     *
     * @return
     */
    public BigDecimal currentCycleChangePercent() {
        return close.subtract(open).subtract(downShadowRatio()).divide(open, 4, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal entityRatio() {
        BigDecimal ratio = new BigDecimal("1").subtract(upShadowRatio()).subtract(downShadowRatio());
        return ratio.compareTo(new BigDecimal("0")) > 0 ? ratio : new BigDecimal("0");
    }

    public BigDecimal upShadowRatio() {
        BigDecimal up = high.subtract(new BigDecimal(Math.max(open.doubleValue(), close.doubleValue()) + ""));
        return eq(up, new BigDecimal("0")) ? new BigDecimal("0") : up.divide(high.subtract(low), 4, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal downShadowRatio() {
        BigDecimal down = new BigDecimal(Math.min(open.doubleValue(), close.doubleValue()) + "").subtract(low);
        return eq(down, new BigDecimal("0")) ? new BigDecimal("0") : down.divide(high.subtract(low), 4, BigDecimal.ROUND_HALF_UP);
    }

    public List<Object> toReportData() {
        List<Object> data = new LinkedList<>();
        data.add(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date));
        data.add(open.doubleValue());
        data.add(close.doubleValue());
        data.add(low.doubleValue());
        data.add(high.doubleValue());
        data.add(volumn != null ? volumn.longValue() : 0);
        return data;
    }

}
