package com.zero.midas.task;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.entity.report.KLineReport;
import com.zero.midas.domain.factory.KLineFactory;
import com.zero.midas.domain.factory.KLineReportFactory;
import com.zero.midas.domain.specification.impl.shape.Venus;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.DailyRepository;
import com.zero.midas.repository.MonthlyRepository;
import com.zero.midas.repository.StockRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AnalysisTask {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private KLineFactory kLineFactory;

    @Autowired
    private KLineReportFactory kLineReportFactory;

    public void analysisMonthly() {
        List<StockDO> stocks = this.stockRepository.list();
        Monitor monitor = new Monitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(stock.getName(), monthlyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析启明星:[{}]TPS", monitor.getCalculate() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    public void analysisDaily() {
        List<StockDO> stocks = this.stockRepository.list();
        Monitor monitor = new Monitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(stock.getName(), dailyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析启明星:[{}]TPS", monitor.getCalculate() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    private void analysis(String name, List<KLineNode> kline, Monitor monitor) {
        for (int i = 1; i < kline.size(); i++) {
            monitor.conut();
            List<KLineNode> kLineNodes = kline.subList(0, i);
            if (kLineFactory.getKLine(kLineNodes).isVenus()) {
                String code = kLineNodes.get(kLineNodes.size() - 1).getCode();
                log.info("[{}][{}]判断为启明星", kLineNodes.get(kLineNodes.size() - 1).getCode(), kLineNodes.get(kLineNodes.size() - 1).getDate());
                // 因为subList不包含i,因此实际聚焦的i是i-1
                KLineReport kLineReport = kLineReportFactory.getKLineReport(code, name, kline, i - 1, Venus.SIZE);
                kLineReport.exportFile();
            }
        }
    }


    @Data
    public static class Monitor {
        private long start;
        private long calculate;

        public void conut() {
            calculate++;
        }
    }
}
