package com.zero.midas.domain.entity.regression;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.entity.report.KLineReport;
import com.zero.midas.domain.factory.KLineFactory;
import com.zero.midas.domain.factory.KLineReportFactory;
import com.zero.midas.domain.specification.KLineShape;
import com.zero.midas.domain.specification.impl.shape.Venus;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.DailyRepository;
import com.zero.midas.repository.MonthlyRepository;
import com.zero.midas.repository.StockRepository;
import com.zero.midas.task.AnalysisTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.Resource;

import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/9/1 22:15
 * @Description:
 */
@Component
@Slf4j
public class VenusRegression extends RegressionTemplate {

    @Autowired
    private Venus venus;

    @Override
    protected KLineShape shape() {
        return venus;
    }
}
