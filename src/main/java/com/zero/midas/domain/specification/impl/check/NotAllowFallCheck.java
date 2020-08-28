package com.zero.midas.domain.specification.impl.check;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.model.dto.CheckResultDTO;
import com.zero.midas.domain.specification.Check;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.zero.midas.utils.BigDecimalUtils.lt;

/**
 * @author: fengzijian
 * @since: 2020/8/28 17:10
 * @Description:
 */
@Component
public class NotAllowFallCheck implements Check {

    @Override
    public CheckResultDTO check(List<KLineNode> kLines) {
        CheckResultDTO checkResultDTO = new CheckResultDTO();
        if (kLines.size() < 2) {
            return checkResultDTO;
        }
        BigDecimal startPrice = kLines.get(0).getClose();
        BigDecimal endPrice = kLines.get(kLines.size() - 1).getClose();
        for (int i = 1; i < (kLines.size() > 15 ? 15 : kLines.size()); i++) {
            KLineNode per = kLines.get(i - 1);
            KLineNode cur = kLines.get(i);
            if (lt(cur.getOpen(), per.getClose())) {
                endPrice = cur.getClose();
                break;
            }
            checkResultDTO.addCycle();
        }
        checkResultDTO.setPercent(endPrice.subtract(startPrice).divide(startPrice, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
        return checkResultDTO;
    }
}
