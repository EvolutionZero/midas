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
public class DownBanding implements KLineShape {

    public static final int SIZE = 1;

    private Config config = new Config();


    @Override
    public String name() {
        return "看跌捉腰带线";
    }

    @Override
    public boolean judge(List<KLineNode> kLines) {
        if (kLines.isEmpty()) {
            return false;
        }
        KLineNode kLineNode = kLines.get(kLines.size() - 1);
        if (kLineNode.isUp()) {
            return false;
        }
        if (gt(kLineNode.upShadowRatio(), config.getUpShadowHighestRatio())) {
            return false;
        }
        if (lt(kLineNode.entityRatio(), config.getEntityLowestRatio())) {
            return false;
        }
        if (gt(kLineNode.currentCycleChangePercent(), config.getDownLowestPercent())) {
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
        private double upShadowHighestRatio = 0.03;
        private double entityLowestRatio = 0.9;
        private double downLowestPercent = -0.03;
    }
}
