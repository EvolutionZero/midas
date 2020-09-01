package com.zero.midas.domain.entity.regression;

import com.zero.midas.domain.entity.kline.KLine;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.entity.report.KLineReport;
import com.zero.midas.domain.factory.KLineReportFactory;
import com.zero.midas.domain.model.dto.CheckResultDTO;
import com.zero.midas.domain.specification.Checker;
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

import java.math.BigDecimal;
import java.util.Comparator;
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
        List<CheckResultDTO> results = new LinkedList<>();
        List<StockDO> stocks = this.stockRepository.list();
        VelocityMonitor monitor = new VelocityMonitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> results.addAll(analysis(stock.getName(), function.apply(stock.getCode()), monitor)));
        log.info("分析{}:[{}]TPS", shape().name(), monitor.getCount() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
        int up = results.stream().filter(r -> gt(r.getPercent(), new BigDecimal("0"))).collect(Collectors.toList()).size();
        List<BigDecimal> percents = results.stream().map(CheckResultDTO::getPercent).collect(Collectors.toList());
        log.info("总收益百分比:{}, 正确率: {}/{} = {}", sum(percents), up, results.size(), up * 1.0 / results.size());
    }

    private List<CheckResultDTO> analysis(String name, List<KLineNode> kline, VelocityMonitor monitor) {
        List<CheckResultDTO> results = new LinkedList<>();
        for (int i = 1; i < kline.size(); i++) {
            monitor.conut();
            List<KLineNode> kLineNodes = kline.subList(0, i);

            if (shape().judge(kLineNodes)) {
                String code = kLineNodes.get(kLineNodes.size() - 1).getCode();
                log.info("[{}][{}]判断为{}",
                        kLineNodes.get(kLineNodes.size() - 1).getCode(),
                        kLineNodes.get(kLineNodes.size() - 1).getDate(),
                        shape().name());
                CheckResultDTO check = checker().check(kline.subList(i, kline.size()));
                results.add(check);
                // 因为subList不包含i,因此实际聚焦的i是i-1
                KLineReport kLineReport = kLineReportFactory.getKLineReport(code, name, kline, i - 1, Venus.SIZE);
                kLineReport.exportFile();
            }
        }
        return results;
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
