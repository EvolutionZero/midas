package com.zero.midas.task;

import com.google.common.collect.Lists;
import com.zero.midas.domain.entity.kline.KLine;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.factory.KLineFactory;
import com.zero.midas.model.entity.MonthlyDO;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.MonthlyRepository;
import com.zero.midas.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AnalysisTask {
    private static final Logger log = LoggerFactory.getLogger(AnalysisTask.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private KLineFactory kLineFactory;

    @Scheduled(cron = "0 10 15 * * ?")
    public void exec() {
        List<StockDO> stocks = this.stockRepository.list();
        stocks.forEach(stockDO -> analysis(stockDO));
    }

    private void analysis(StockDO stock) {
        List<MonthlyDO> monthlyDOS = monthlyRepository.listByCode(stock.getCode());
        for (int i = 1; i < monthlyDOS.size(); i++) {
            List<KLineNode> kLineNodes = Lists.newArrayList(monthlyDOS).subList(0, i).stream().map(monthlyDO -> {
                KLineNode node = new KLineNode();
                BeanUtils.copyProperties(monthlyDO, node);
                return node;
            }).collect(Collectors.toList());
            KLine kLine = kLineFactory.getKLine(kLineNodes);
            if (kLine.isVenus()) {
                log.info("[{}][{}]判断为启明星", monthlyDOS.get(i).getCode(), monthlyDOS.get(i).getDate());
            }
        }
    }
}
