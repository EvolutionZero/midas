package com.zero.midas.utils;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/8/10 16:23
 * @Description:
 */
@AllArgsConstructor
public class Line {

    private List<BigDecimal> data;

    /**
     * 是否顶部
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static boolean isTop(BigDecimal x, BigDecimal y, BigDecimal z) {
        return y.doubleValue() > x.doubleValue() && y.doubleValue() > z.doubleValue();
    }

    /**
     * 是否底部
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static boolean isButtom(BigDecimal x, BigDecimal y, BigDecimal z) {
        return y.doubleValue() < x.doubleValue() && y.doubleValue() < z.doubleValue();
    }

    /**
     * 取最大值
     *
     * @return
     */
    public BigDecimal max() {
        return data.stream().max(Comparator.naturalOrder()).orElse(new BigDecimal(0));
    }

    /**
     * 取最小值
     *
     * @return
     */
    public BigDecimal min() {
        return data.stream().max(Comparator.reverseOrder()).orElse(new BigDecimal(0));
    }
}
