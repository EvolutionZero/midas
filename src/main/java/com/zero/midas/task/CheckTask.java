package com.zero.midas.task;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.factory.KLineFactory;
import com.zero.midas.domain.factory.KLineReportFactory;
import com.zero.midas.domain.model.dto.CheckResultDTO;
import com.zero.midas.domain.specification.impl.check.NotAllowFallCheck;
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

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zero.midas.utils.BigDecimalUtils.gt;
import static com.zero.midas.utils.BigDecimalUtils.sum;

@Component
@Slf4j
public class CheckTask {
    private static final Logger log = LoggerFactory.getLogger(CheckTask.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private KLineFactory kLineFactory;

    @Autowired
    private NotAllowFallCheck notAllowFallCheck;

    @Autowired
    private KLineReportFactory kLineReportFactory;

    public void analysisMonthly() {
        List<StockDO> stocks = this.stockRepository.list();
        Monitor monitor = new Monitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> analysis(monthlyRepository.listKLine(stock.getCode()), monitor));
        log.info("分析:[{}]TPS", monitor.getCalculate() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
    }

    public void analysisDaily() {
        List<CheckResultDTO> results = new LinkedList<>();
        List<StockDO> stocks = this.stockRepository.list();
        Monitor monitor = new Monitor();
        monitor.setStart(System.currentTimeMillis());
        stocks.forEach(stock -> results.addAll(analysis(dailyRepository.listKLine(stock.getCode()), monitor)));
        log.info("分析:[{}]TPS", monitor.getCalculate() * 1.0 / ((System.currentTimeMillis() - monitor.getStart()) / 1000.0));
        int up = results.stream().filter(r -> gt(r.getPercent(), new BigDecimal("0"))).collect(Collectors.toList()).size();
        List<BigDecimal> percents = results.stream().map(CheckResultDTO::getPercent).collect(Collectors.toList());
        List<Double> collect = percents.stream().filter(p -> !new BigDecimal("0").equals(p)).sorted(Comparator.naturalOrder()).map(BigDecimal::doubleValue).collect(Collectors.toList());
        System.out.println(collect);
        log.info("总共百分比:{}, 正确率:{}", sum(percents), up * 1.0 / results.size());
    }

    private List<CheckResultDTO> analysis(List<KLineNode> kline, Monitor monitor) {
        List<CheckResultDTO> results = new LinkedList<>();
        for (int i = 1; i < kline.size(); i++) {
            monitor.conut();
            List<KLineNode> kLineNodes = kline.subList(0, i);
            if (kLineFactory.getKLine(kLineNodes).isVenus()) {
                CheckResultDTO check = notAllowFallCheck.check(kline.subList(i, kline.size()));
                results.add(check);
                log.info("判断结果:{}", check.toString());
            }
        }
        return results;
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
