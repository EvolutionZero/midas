package com.zero.midas.domain.strategy.impl;


import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.strategy.Pattern;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Cross implements Pattern {

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.isEmpty()) {
            return false;
        }
        KLineNode kLineNode = kLines.get(kLines.size() - 1);
        return kLineNode.entityRatio().compareTo(new BigDecimal("0.01")) < 0;
    }
}
