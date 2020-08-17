package com.zero.midas.domain.strategy.impl;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.strategy.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/8/17 16:43
 * @Description:
 */
@Component
public class Venus implements Pattern {

    @Autowired
    private Cross cross;

    @Override
    public boolean judge(List<KLineNode> kLines) {
        return true;
    }
}