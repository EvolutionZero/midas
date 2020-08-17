package com.zero.midas.domain.factory;

import com.zero.midas.domain.entity.kline.KLine;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.strategy.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: fengzijian
 * @since: 2020/8/17 16:56
 * @Description:
 */
@Component
public class KLineFactory {
    @Autowired
    private Map<String, Pattern> patterns;

    public KLine getKLine(List<KLineNode> kLines) {
        return new KLine(patterns, kLines);
    }
}
