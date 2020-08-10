package com.zero.midas.domain.event.listener;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zero.midas.domain.event.model.WeeklyTradeDataStorageEvent;
import com.zero.midas.domain.model.dto.TradeDTO;
import com.zero.midas.model.entity.WeeklyDO;
import com.zero.midas.repository.WeeklyRepository;
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
public class WeeklyTradeDataStorageListener implements ApplicationListener<WeeklyTradeDataStorageEvent> {
    private static final Logger log = LoggerFactory.getLogger(WeeklyTradeDataStorageListener.class);

    @Autowired
    private WeeklyRepository weeklyRepository;

    @Async
    @Override
    public void onApplicationEvent(WeeklyTradeDataStorageEvent event) {
        try {
            event.getThreshold().release();
            List<TradeDTO> trades = event.getTrades();
            if (trades.isEmpty()) {
                return;
            }
            List<LocalDate> existDates = listExistDaliyData((trades.get(0)).getCode()).stream().map(WeeklyDO::getDate).collect(Collectors.toList());
            this.weeklyRepository.saveBatch(trades
                    .stream()
                    .filter(trade -> !existDates.contains(trade.getDate()))
                    .map(trade -> {
                        WeeklyDO dailyDO = new WeeklyDO();
                        BeanUtils.copyProperties(trade, dailyDO);
                        return dailyDO;
                    }).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(String.format("[%s]存储数据出错)", event.getTrades().get(0).getCode()), e);
        }
    }

    private List<WeeklyDO> listExistDaliyData(String code) {
        QueryWrapper<WeeklyDO> conditions = new QueryWrapper();
        conditions.eq("code", code);
        return this.weeklyRepository.list((Wrapper) conditions);
    }
}
