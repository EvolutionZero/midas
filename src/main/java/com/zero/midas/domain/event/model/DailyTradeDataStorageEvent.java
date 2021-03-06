package com.zero.midas.domain.event.model;

import com.zero.midas.domain.model.dto.TradeDTO;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.concurrent.Semaphore;

@Data
public class DailyTradeDataStorageEvent
        extends ApplicationEvent {
    private List<TradeDTO> trades;
    private Semaphore threshold;

    public DailyTradeDataStorageEvent(List<TradeDTO> trades, Semaphore threshold) {
        super(trades);
        this.trades = trades;
        this.threshold = threshold;
    }
}
