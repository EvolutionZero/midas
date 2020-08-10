package com.zero.midas.remote.facade;

import com.zero.midas.remote.IFengFinanceRemote;
import com.zero.midas.remote.model.dto.IFengTradeDTO;
import com.zero.midas.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class IFengFinanceFacade {
    private static final String EMPTY_JSON = "{\"record\":{}}";
    private IFengFinanceRemote delegate;

    public IFengFinanceFacade(IFengFinanceRemote delegate) {
        this.delegate = delegate;
    }

    public Optional<IFengTradeDTO> queryDaily(String code) {
        String json = this.delegate.queryDaily(code);
        return (StringUtils.isEmpty(json)) || (EMPTY_JSON.equals(json)) ? Optional.empty() : Optional.of(JsonUtils.parse(json, IFengTradeDTO.class));
    }

    public Optional<IFengTradeDTO> queryWeekly(String code) {
        String json = this.delegate.queryWeekly(code);
        return (StringUtils.isEmpty(json)) || (EMPTY_JSON.equals(json)) ? Optional.empty() : Optional.of(JsonUtils.parse(json, IFengTradeDTO.class));
    }

    public Optional<IFengTradeDTO> queryMonthly(String code) {
        String json = this.delegate.queryMonthly(code);
        return (StringUtils.isEmpty(json)) || (EMPTY_JSON.equals(json)) ? Optional.empty() : Optional.of(JsonUtils.parse(json, IFengTradeDTO.class));
    }
}
