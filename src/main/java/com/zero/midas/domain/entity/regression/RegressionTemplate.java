package com.zero.midas.domain.entity.regression;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.entity.report.KLineReport;
import com.zero.midas.domain.factory.KLineReportFactory;
import com.zero.midas.domain.model.dto.CheckResultDTO;
import com.zero.midas.domain.specification.Checker;
import com.zero.midas.domain.specification.KLineShape;
import com.zero.midas.model._do.WatchWindow;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.DailyRepository;
import com.zero.midas.repository.MonthlyRepository;
import com.zero.midas.repository.StockRepository;
import com.zero.midas.repository.WeeklyRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zero.midas.utils.BigDecimalUtils.gt;
import static com.zero.midas.utils.BigDecimalUtils.sum;

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

    protected abstract Checker checker();


    public void daily() {
        analysis( code -> dailyRepository.listKLine(code));
    }


    public void weekly() {
        analysis( code -> weeklyRepository.listKLine(code));
    }

    public void monthly() {
        analysis( code -> monthlyRepository.listKLine(code));
    }

    public void analysis(Function<String, List<KLineNode>> function) {
        List<RegressionRecord> results = new LinkedList<>();
        List<StockDO> stocks = this.stockRepository.list();
        VelocityMonitor monitor = new VelocityMonitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> results.addAll(analysis(stock.getName(), function.apply(stock.getCode()), monitor)));
        log.info("分析{}:[{}]TPS", shape().name(), monitor.getCount() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
        int up = results.stream().map(RegressionRecord::getCheck).filter(r -> gt(r.getPercent(), new BigDecimal("0"))).collect(Collectors.toList()).size();
        List<BigDecimal> percents = results.stream().map(RegressionRecord::getCheck).map(CheckResultDTO::getPercent).collect(Collectors.toList());
        log.info("总收益百分比:{}, 正确率: {}/{} = {}", sum(percents), up, results.size(), up * 1.0 / results.size());
        report(results);
    }

    private List<RegressionRecord> analysis(String name, List<KLineNode> kline, VelocityMonitor monitor) {
        List<RegressionRecord> results = new LinkedList<>();
        for (int i = 0; i < kline.size(); i++) {
            monitor.conut();
            WatchWindow watchWindow = new WatchWindow(i, kline);
            watchWindow.setName(name);
            if (shape().judge(watchWindow.judegeNodes())) {
                String code = watchWindow.getCode();
                log.info("[{}][{}]判断为{}",
                        code,
                        watchWindow.focusNode().getDate(),
                        shape().name());
                CheckResultDTO check = checker().check(kline.subList(i, kline.size()));
                KLineReport report = kLineReportFactory.getKLineReport(watchWindow, shape().size());
                String subDir = shape().name() + "/" + (check.correct() ? "正确" : check.balance() ? "平衡" : "错误");
                report.getConfig().setOutputDir(report.getConfig().getOutputDir() + subDir);

                results.add(new RegressionRecord(check, report));
            }
        }
        return results;
    }

    protected void report(List<RegressionRecord> records){
        records.stream().map(RegressionRecord::getReport).forEach(KLineReport::exportFile);
    }


    /**
     * 回归记录，对应单交易周期预测结果记录
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegressionRecord{
        private CheckResultDTO check;
        private KLineReport report;
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
