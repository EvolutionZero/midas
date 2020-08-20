package com.zero.midas.domain.entity.collector;

import com.zero.midas.domain.factory.IFengCollectorFactory;
import com.zero.midas.domain.model.dto.TradeDTO;
import com.zero.midas.remote.facade.IFengFinanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IFengCollector {
    private String code;
    @Autowired
    private IFengFinanceFacade client;
    @Autowired
    private IFengCollectorFactory factory;

    public IFengCollector(String code) {
        this.code = code;
    }

    public List<TradeDTO> daliyTrade() {
        return this.factory.toTradeDTOList(this.code, this.client.queryDaily(this.code));
    }

    public List<TradeDTO> weeklyTrade() {
        List<TradeDTO> tradeDTOS = this.factory.toTradeDTOList(this.code, this.client.queryWeekly(this.code));
        if (!tradeDTOS.isEmpty()) {
            LocalDate lastTradeDate = tradeDTOS.get(tradeDTOS.size() - 1).getDate();
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
            if (LocalDate.now().getYear() == lastTradeDate.getYear()
                    && LocalDate.now().get(weekFields.weekOfYear()) == lastTradeDate.get(weekFields.weekOfYear())) {
                tradeDTOS = tradeDTOS.subList(0, tradeDTOS.size() - 1);
            }
        }
        return tradeDTOS;
    }

    public List<TradeDTO> monthlyTrade() {
        List<TradeDTO> tradeDTOS = this.factory.toTradeDTOList(this.code, this.client.queryMonthly(this.code));
        if (!tradeDTOS.isEmpty()) {
            LocalDate lastTradeDate = tradeDTOS.get(tradeDTOS.size() - 1).getDate();
            if (LocalDate.now().getYear() == lastTradeDate.getYear()
                    && LocalDate.now().getMonthValue() == lastTradeDate.getMonthValue()) {
                tradeDTOS = tradeDTOS.subList(0, tradeDTOS.size() - 1);
            }
        }
        return tradeDTOS;
    }

}
