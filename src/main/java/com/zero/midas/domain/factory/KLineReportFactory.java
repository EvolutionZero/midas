package com.zero.midas.domain.factory;

import com.zero.midas.domain.entity.report.KLineReport;
import com.zero.midas.model._do.WatchWindow;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: fengzijian
 * @since: 2020/8/17 16:56
 * @Description:
 */
@Component
public class KLineReportFactory {
    @Autowired
    private BeanFactory beanFactory;

    public KLineReport getKLineReport(WatchWindow watchWindow, int focusSize) {
        return beanFactory.getBean(KLineReport.class, watchWindow, new KLineReport.Config().setFocusBackOffSize(focusSize));
    }
}
