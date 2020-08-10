package com.zero.midas.domain.event.listener;

import com.zero.midas.domain.event.model.IFengCollectorWeeklyEvent;
import com.zero.midas.domain.event.model.WeeklyTradeDataStorageEvent;
import com.zero.midas.domain.manager.CollectorManager;
import com.zero.midas.domain.model.dto.TradeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IFengCollectorWeeklyListener
        implements ApplicationListener<IFengCollectorWeeklyEvent> {
    private static final Logger log = LoggerFactory.getLogger(IFengCollectorWeeklyListener.class);
    @Autowired
    private CollectorManager collectorManager;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Async
    @Override
    public void onApplicationEvent(IFengCollectorWeeklyEvent event) {
        try {
            log.info("接收采集[{}]周线事件,剩余[{}]张允许证", event.getCode(), Integer.valueOf(event.getThreshold().availablePermits()));
            List<TradeDTO> tradeDTOS = this.collectorManager.createIFengCollector(event.getCode()).weeklyTrade();
            log.info("[{}]的周线数据:[{}]条", event.getCode(), Integer.valueOf(tradeDTOS.size()));
            if (!tradeDTOS.isEmpty()) {
                this.publisher.publishEvent(new WeeklyTradeDataStorageEvent(tradeDTOS, event.getThreshold()));
            } else {
                event.getThreshold().release();
            }
        } catch (Exception e) {
            event.getThreshold().release();
            log.error(String.format("[%s]采集周线失败", new Object[]{event.getCode()}), e);
        }
    }
}
