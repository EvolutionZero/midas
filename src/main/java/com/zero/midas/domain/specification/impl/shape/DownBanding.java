package com.zero.midas.domain.specification.impl.shape;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.specification.KLineShape;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.gt;
import static com.zero.midas.utils.BigDecimalUtils.lt;

/**
 * 捉腰带
 *
 * @author: fengzijian
 * @since: 2020/8/18 18:10
 * @Description:
 */
@Component
public class DownBanding implements KLineShape {

    public static final int SIZE = 1;

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.isEmpty()) {
            return false;
        }
        KLineNode kLineNode = kLines.get(kLines.size() - 1);
        if (kLineNode.isUp()) {
            return false;
        }
        if (gt(kLineNode.upShadowRatio(), 0.03)) {
            return false;
        }
        if (lt(kLineNode.entityRatio(), 0.9)) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return SIZE;
    }
}
