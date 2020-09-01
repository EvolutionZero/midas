package com.zero.midas.domain.entity.regression;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.entity.report.KLineReport;
import com.zero.midas.domain.factory.KLineReportFactory;
import com.zero.midas.domain.specification.KLineShape;
import com.zero.midas.domain.specification.impl.shape.Venus;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.DailyRepository;
import com.zero.midas.repository.MonthlyRepository;
import com.zero.midas.repository.StockRepository;
import com.zero.midas.repository.WeeklyRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/9/1 22:21
 * @Description:
 */
@Component
@Slf4j
public abstract class RegressionTemplate {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private WeeklyRepository weeklyRepository;

    @Autowired
    private KLineReportFactory kLineReportFactory;

    protected abstract KLineShape shape();


    public void daily() {
        List<StockDO> stocks = this.stockRepository.list();
        VelocityMonitor monitor = new VelocityMonitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(stock.getName(), dailyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析{}:[{}]TPS", shape().name(), monitor.getCount() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }


    public void weekly() {
        List<StockDO> stocks = this.stockRepository.list();
        VelocityMonitor monitor = new VelocityMonitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(stock.getName(), weeklyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析{}:[{}]TPS", shape().name(), monitor.getCount() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    public void monthly() {
        List<StockDO> stocks = this.stockRepository.list();
        VelocityMonitor monitor = new VelocityMonitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(stock.getName(), monthlyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析{}:[{}]TPS", shape().name(), monitor.getCount() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    private void analysis(String name, List<KLineNode> kline, VelocityMonitor monitor) {
        for (int i = 1; i < kline.size(); i++) {
            monitor.conut();
            List<KLineNode> kLineNodes = kline.subList(0, i);

            if (shape().judge(kLineNodes)) {
                String code = kLineNodes.get(kLineNodes.size() - 1).getCode();
                log.info("[{}][{}]判断为{}",
                        kLineNodes.get(kLineNodes.size() - 1).getCode(),
                        kLineNodes.get(kLineNodes.size() - 1).getDate(),
                        shape().name());
                // 因为subList不包含i,因此实际聚焦的i是i-1
                KLineReport kLineReport = kLineReportFactory.getKLineReport(code, name, kline, i - 1, Venus.SIZE);
                kLineReport.exportFile();
            }
        }
    }

    /**
     * 速度监视器
     */
    @Data
    public static class VelocityMonitor {
        private long start;
        private long count;

        public void conut() {
            count++;
        }
    }

}
