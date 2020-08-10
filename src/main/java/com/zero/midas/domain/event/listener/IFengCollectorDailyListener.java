package com.zero.midas.domain.event.listener;

import com.zero.midas.domain.event.model.DailyTradeDataStorageEvent;
import com.zero.midas.domain.event.model.IFengCollectorDailyEvent;
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
public class IFengCollectorDailyListener
        implements ApplicationListener<IFengCollectorDailyEvent> {
    private static final Logger log = LoggerFactory.getLogger(IFengCollectorDailyListener.class);
    @Autowired
    private CollectorManager collectorManager;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Async
    public void onApplicationEvent(IFengCollectorDailyEvent event) {
        try {
            log.info("接收采集[{}]日线事件,剩余[{}]张允许证", event.getCode(), Integer.valueOf(event.getThreshold().availablePermits()));
            List<TradeDTO> tradeDTOS = this.collectorManager.createIFengCollector(event.getCode()).daliyTrade();
            log.info("[{}]的日线数据:[{}]条", event.getCode(), Integer.valueOf(tradeDTOS.size()));
            if (!tradeDTOS.isEmpty()) {
                this.publisher.publishEvent(new DailyTradeDataStorageEvent(tradeDTOS, event.getThreshold()));
            } else {
                event.getThreshold().release();
            }
        } catch (Exception e) {
            event.getThreshold().release();
            log.error(String.format("[%s]采集日线失败", new Object[]{event.getCode()}), e);
        }
    }
}
