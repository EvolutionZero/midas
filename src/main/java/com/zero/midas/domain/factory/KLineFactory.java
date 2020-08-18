package com.zero.midas.domain.factory;

import com.zero.midas.domain.entity.kline.KLine;
import com.zero.midas.domain.entity.kline.KLineNode;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/8/17 16:56
 * @Description:
 */
@Component
public class KLineFactory {
    @Autowired
    private BeanFactory beanFactory;

    public KLine getKLine(List<KLineNode> kLines) {
        return beanFactory.getBean(KLine.class, kLines);
    }
}
