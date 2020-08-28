package com.zero.midas.domain.specification.impl;

import com.google.common.collect.Lists;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.specification.KLineShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.gte;
import static com.zero.midas.utils.BigDecimalUtils.lte;

/**
 * @author: fengzijian
 * @since: 2020/8/17 16:43
 * @Description:
 */
@Component
public class Venus implements KLineShape {

    public static final int SIZE = 3;

    @Autowired
    private Cross cross;

    @Autowired
    private DownBanding downBanding;

    @Autowired
    private UpBanding upBanding;

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.size() < 3) {
            return false;
        }
        KLineNode downNode = kLines.get(kLines.size() - 3);
        KLineNode crossNode = kLines.get(kLines.size() - 2);
        KLineNode upNode = kLines.get(kLines.size() - 1);

        if (downBanding.judge(Lists.newArrayList(downNode))
                && cross.judge(Lists.newArrayList(crossNode))
                && upBanding.judge(Lists.newArrayList(upNode))
                && lte(crossNode.getOpen(), downNode.getClose())
                && gte(upNode.getOpen(), crossNode.getClose())) {
            return true;
        }

        return false;
    }


    @Override
    public int size() {
        return SIZE;
    }
}
