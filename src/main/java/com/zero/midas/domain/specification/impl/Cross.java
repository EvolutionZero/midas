package com.zero.midas.domain.specification.impl;


import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.specification.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.lte;

@Component
public class Cross implements Specification {

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.isEmpty()) {
            return false;
        }
        KLineNode kLineNode = kLines.get(kLines.size() - 1);
        return lte(kLineNode.entityRatio(), 0.01);
    }
}
