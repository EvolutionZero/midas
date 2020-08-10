package com.zero.midas.domain.event.model;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.concurrent.Semaphore;

@Data
public class IFengCollectorDailyEvent
        extends ApplicationEvent {
    private String code;
    private Semaphore threshold;

    public IFengCollectorDailyEvent(String code, Semaphore threshold) {
        super(code);
        this.code = code;
        this.threshold = threshold;
    }

}
