package com.zero.midas.domain.factory;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.entity.report.KLineReport;
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
public class KLineReportFactory {
    @Autowired
    private BeanFactory beanFactory;

    public KLineReport getKLineReport(String code, String name, List<KLineNode> kLines, int focusIndex, int focusSize) {
        return beanFactory.getBean(KLineReport.class, code, name, kLines, new KLineReport.Config().setFocusIndex(focusIndex).setFocusBackOffSize(focusSize));
    }

    public KLineReport getKLineReport(String code, String name, List<KLineNode> kLines, KLineReport.Config config) {
        return beanFactory.getBean(KLineReport.class, code, name, kLines, config);
    }
}
