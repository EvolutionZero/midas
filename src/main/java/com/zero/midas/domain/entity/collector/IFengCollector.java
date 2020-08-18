package com.zero.midas.domain.entity.collector;

import com.zero.midas.domain.factory.IFengCollectorFactory;
import com.zero.midas.domain.model.dto.TradeDTO;
import com.zero.midas.remote.facade.IFengFinanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        return this.factory.toTradeDTOList(this.code, this.client.queryWeekly(this.code));
    }

    public List<TradeDTO> monthlyTrade() {
        return this.factory.toTradeDTOList(this.code, this.client.queryMonthly(this.code));
    }

}
