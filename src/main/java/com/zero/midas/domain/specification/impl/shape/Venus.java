package com.zero.midas.domain.specification.impl.shape;

import com.google.common.collect.Lists;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.specification.KLineShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.gt;
import static com.zero.midas.utils.BigDecimalUtils.lt;

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
        if (kLines.size() < SIZE) {
            return false;
        }
        KLineNode downNode = kLines.get(kLines.size() - 3);
        KLineNode crossNode = kLines.get(kLines.size() - 2);
        KLineNode upNode = kLines.get(kLines.size() - 1);

        if (!isDownBanding(downNode)) {
            return false;
        }

        if (!isCross(crossNode)) {
            return false;
        }

        if (!isUpBanding(upNode)) {
            return false;
        }

        if (gt(crossNode.getOpen(), downNode.getClose())) {
            return false;
        }

        if (lt(upNode.getOpen(), crossNode.getClose())) {
            return false;
        }


        return true;
    }

    private boolean isDownBanding(KLineNode node) {
        return downBanding.judge(Lists.newArrayList(node));
    }

    private boolean isCross(KLineNode node) {
        return cross.judge(Lists.newArrayList(node));
    }

    private boolean isUpBanding(KLineNode node) {
        return upBanding.judge(Lists.newArrayList(node));
    }

    @Override
    public int size() {
        return SIZE;
    }
}
