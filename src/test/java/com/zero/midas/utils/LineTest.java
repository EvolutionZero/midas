package com.zero.midas.utils;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author: fengzijian
 * @since: 2020/8/10 16:32
 * @Description:
 */
public class LineTest {

    @Test
    public void max() {
        BigDecimal max = new Line(Lists.newArrayList(1, 2, 3).stream().map(BigDecimal::new).collect(Collectors.toList())).max();
        assertEquals(3, max.intValue());
    }

    @Test
    public void min() {
        BigDecimal min = new Line(Lists.newArrayList(1, 2, 3).stream().map(BigDecimal::new).collect(Collectors.toList())).min();
        assertEquals(1, min.intValue());
    }
}