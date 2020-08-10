package com.zero.midas.domain.event.listener;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zero.midas.domain.event.model.DailyTradeDataStorageEvent;
import com.zero.midas.domain.model.dto.TradeDTO;
import com.zero.midas.model.entity.DailyDO;
import com.zero.midas.repository.DailyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyTradeDataStorageListener implements ApplicationListener<DailyTradeDataStorageEvent> {
    private static final Logger log = LoggerFactory.getLogger(DailyTradeDataStorageListener.class);

    @Autowired
    private DailyRepository dailyRepository;

    @Async
    @Override
    public void onApplicationEvent(DailyTradeDataStorageEvent event) {
        try {
            event.getThreshold().release();
            List<TradeDTO> trades = event.getTrades();
            if (trades.isEmpty()) {
                return;
            }
            List<LocalDate> existDates = listExistDaliyData((trades.get(0)).getCode()).stream().map(DailyDO::getDate).collect(Collectors.toList());
            this.dailyRepository.saveBatch(trades
                    .stream()
                    .filter(trade -> !existDates.contains(trade.getDate()))
                    .map(trade -> {
                        DailyDO dailyDO = new DailyDO();
                        BeanUtils.copyProperties(trade, dailyDO);
                        return dailyDO;
                    }).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(String.format("[%s]存储数据出错)", event.getTrades().get(0).getCode()), e);
        }
    }

    private List<DailyDO> listExistDaliyData(String code) {
        QueryWrapper<DailyDO> conditions = new QueryWrapper();
        conditions.eq("code", code);
        return this.dailyRepository.list((Wrapper) conditions);
    }
}
