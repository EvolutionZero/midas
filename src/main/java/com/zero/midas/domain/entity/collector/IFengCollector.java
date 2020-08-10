package com.zero.midas.domain.entity.collector;

import com.zero.midas.domain.factory.IFengCollectorFactory;
import com.zero.midas.domain.model.dto.TradeDTO;
import com.zero.midas.remote.facade.IFengFinanceFacade;

import java.util.List;

public class IFengCollector {
    private String code;
    private IFengFinanceFacade client;
    private IFengCollectorFactory factory;

    public IFengCollector(String code, IFengFinanceFacade client, IFengCollectorFactory factory) {
        this.code = code;
        this.client = client;
        this.factory = factory;
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
