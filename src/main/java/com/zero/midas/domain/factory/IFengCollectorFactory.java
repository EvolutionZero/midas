package com.zero.midas.domain.factory;

import com.zero.midas.domain.model.dto.TradeDTO;
import com.zero.midas.exception.MidasException;
import com.zero.midas.remote.model.dto.IFengTradeDTO;
import com.zero.midas.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IFengCollectorFactory {

    public List<TradeDTO> toTradeDTOList(String code, Optional<IFengTradeDTO> iFengTradeDTO) {
        return iFengTradeDTO.map(IFengTradeDTO::getRecord).orElse(new LinkedList<>())
                .stream().map(trade -> toTradeDTO(code, trade)).collect(Collectors.toList());
    }

    private TradeDTO toTradeDTO(String code, List<String> data) {
        try {
            TradeDTO tradeDTO = new TradeDTO();
            tradeDTO.setCode(code);
            tradeDTO.setDate(TimeUtils.parseLocalDate(data.get(0)));
            tradeDTO.setOpen(getBigDecimal(data, 1));
            tradeDTO.setHigh(getBigDecimal(data, 2));
            tradeDTO.setClose(getBigDecimal(data, 3));
            tradeDTO.setLow(getBigDecimal(data, 4));
            tradeDTO.setVolumn(getBigDecimal(data, 5));
            tradeDTO.setPriceChange(getBigDecimal(data, 6));
            tradeDTO.setPriceRatio(getBigDecimal(data, 7));
            tradeDTO.setMa5(getBigDecimal(data, 8));
            tradeDTO.setMa10(getBigDecimal(data, 9));
            tradeDTO.setMa20(getBigDecimal(data, 10));
            tradeDTO.setVma5(getBigDecimal(data, 11));
            tradeDTO.setVma10(getBigDecimal(data, 12));
            tradeDTO.setVma20(getBigDecimal(data, 13));
            if (data.size() > 14)
                tradeDTO.setTurnover(getBigDecimal(data, 14));
            return tradeDTO;
        } catch (Exception e) {
            throw new MidasException(String.format("[%s]转换数据错误", code), e);
        }
    }

    private BigDecimal getBigDecimal(List<String> data, int i) {
        return (data.get(i) == null || StringUtils.isEmpty(data.get(i))) ? null : new BigDecimal((data.get(i)).replace(",", ""));
    }
}
