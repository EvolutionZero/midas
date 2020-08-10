package com.zero.midas.task;

import com.zero.midas.config.MidasProperties;
import com.zero.midas.domain.event.model.IFengCollectorDailyEvent;
import com.zero.midas.domain.event.model.IFengCollectorMonthlyEvent;
import com.zero.midas.domain.event.model.IFengCollectorWeeklyEvent;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Semaphore;

@Component
public class BaseDataCollectTask {
    private static final Logger log = LoggerFactory.getLogger(BaseDataCollectTask.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MidasProperties midasProperties;

    @Scheduled(cron = "0 10 15 * * ?")
    public void exec() {
        daily();
        weekly();
        monthly();
    }

    public void daily() {
        LocalDateTime start = LocalDateTime.now();
        Semaphore threshold = new Semaphore(this.midasProperties.getThreshold().intValue());
        List<StockDO> stocks = this.stockRepository.list();
        stocks.forEach(stock -> {
            try {
                threshold.acquire();
                this.publisher.publishEvent(new IFengCollectorDailyEvent(stock.getCode(), threshold));
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        long duration = Duration.between(start, LocalDateTime.now()).toMillis() / 1000L;
        log.info("日线采集耗时[{}]秒,[{}]TPS", Long.valueOf(duration), (new DecimalFormat("#.##")).format(stocks.size() * 1.0D / duration));
    }

    public void weekly() {
        LocalDateTime start = LocalDateTime.now();
        Semaphore threshold = new Semaphore(this.midasProperties.getThreshold().intValue());
        List<StockDO> stocks = this.stockRepository.list();
        stocks.forEach(stock -> {
            try {
                threshold.acquire();
                this.publisher.publishEvent(new IFengCollectorWeeklyEvent(stock.getCode(), threshold));
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        long duration = Duration.between(start, LocalDateTime.now()).toMillis() / 1000L;
        log.info("周线采集耗时[{}]秒,[{}]TPS", Long.valueOf(duration), (new DecimalFormat("#.##")).format(stocks.size() * 1.0D / duration));
    }

    public void monthly() {
        LocalDateTime start = LocalDateTime.now();
        Semaphore threshold = new Semaphore(this.midasProperties.getThreshold().intValue());
        List<StockDO> stocks = this.stockRepository.list();
        stocks.forEach(stock -> {
            try {
                threshold.acquire();
                this.publisher.publishEvent(new IFengCollectorMonthlyEvent(stock.getCode(), threshold));
            } catch (InterruptedException e) {
                log.error("", e);
            }
        });
        long duration = Duration.between(start, LocalDateTime.now()).toMillis() / 1000L;
        log.info("月线采集耗时[{}]秒,[{}]TPS", Long.valueOf(duration), (new DecimalFormat("#.##")).format(stocks.size() * 1.0D / duration));
    }
}
