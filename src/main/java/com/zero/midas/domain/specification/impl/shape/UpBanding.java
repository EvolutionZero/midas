package com.zero.midas.domain.specification.impl.shape;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.specification.KLineShape;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpBanding implements KLineShape {

    public static final int SIZE = 1;

    private Config config = new Config();

    @Override
    public String name() {
        return "看涨捉腰带线";
    }

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.isEmpty()) {
            return false;
        }
        KLineNode kLineNode = kLines.get(kLines.size() - 1);
        if (kLineNode.isDown()) {
            return false;
        }
        if (gt(kLineNode.downShadowRatio(), config.getDownShadowHighestRatio())) {
            return false;
        }
        if (lt(kLineNode.entityRatio(), config.getEntityLowestRatio())) {
            return false;
        }
        if (lt(kLineNode.currentCycleChangePercent(), config.getUpLowestPercent())) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return SIZE;
    }


    @Data
    public static class Config {
        private double downShadowHighestRatio = 0.03;
        private double entityLowestRatio = 0.8;
        private double upLowestPercent = 0.01;
    }
}
