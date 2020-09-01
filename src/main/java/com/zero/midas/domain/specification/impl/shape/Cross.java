package com.zero.midas.domain.specification.impl.shape;


import com.google.common.collect.Lists;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.specification.KLineShape;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.lte;

@Component
public class Cross implements KLineShape {

    public static final int SIZE = 1;


    @Override
    public String name() {
        return "十字星";
    }

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.isEmpty()) {
            return false;
        }
        KLineNode kLineNode = kLines.get(kLines.size() - 1);
        return lte(kLineNode.entityRatio(), 0.01);
    }

    @Override
    public int size() {
        return SIZE;
    }


    public static void main(String[] args) {
        KLineNode kLineNode = new KLineNode();
        kLineNode.setHigh(new BigDecimal("2.5"));
        kLineNode.setClose(new BigDecimal("2.42"));
        kLineNode.setOpen(new BigDecimal("2.15"));
        kLineNode.setLow(new BigDecimal("2.15"));

        System.out.println(new Cross().judge(Lists.newArrayList(kLineNode)));
    }

}
