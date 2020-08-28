package com.zero.midas.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/8/18 18:13
 * @Description:
 */
public class BigDecimalUtils {

    public static boolean lt(BigDecimal x, double y) {
        return lt(x, new BigDecimal(y + ""));
    }

    public static boolean lt(BigDecimal x, BigDecimal y) {
        return x.compareTo(new BigDecimal(y + "")) < 0;
    }

    public static boolean lte(BigDecimal x, double y) {
        return lte(x, new BigDecimal(y + ""));
    }

    public static boolean lte(BigDecimal x, BigDecimal y) {
        return x.compareTo(new BigDecimal(y + "")) <= 0;
    }

    public static boolean gt(BigDecimal x, double y) {
        return gt(x, new BigDecimal(y + ""));
    }

    public static boolean gt(BigDecimal x, BigDecimal y) {
        return x.compareTo(new BigDecimal(y + "")) > 0;
    }

    public static boolean gte(BigDecimal x, double y) {
        return gte(x, new BigDecimal(y + ""));
    }

    public static boolean gte(BigDecimal x, BigDecimal y) {
        return x.compareTo(new BigDecimal(y + "")) >= 0;
    }

    public static boolean eq(BigDecimal x, double y) {
        return eq(x, new BigDecimal(y + ""));
    }

    public static boolean eq(BigDecimal x, BigDecimal y) {
        return x.compareTo(new BigDecimal(y + "")) == 0;
    }

    public static BigDecimal sum(List<BigDecimal> datas) {
        BigDecimal sum = new BigDecimal("0");
        for (BigDecimal data : datas) {
            sum = sum.add(data);
        }
        return sum;
    }
}
