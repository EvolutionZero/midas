package com.zero.midas.domain.strategy.impl;


import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.strategy.Pattern;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cross implements Pattern {

    @Override
    public boolean judge(List<KLineNode> kLines) {
        return true;
    }
}
