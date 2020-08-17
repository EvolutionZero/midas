package com.zero.midas.domain.strategy.impl;

import com.zero.midas.domain.model.dto.KLineDTO;
import com.zero.midas.domain.strategy.Pattern;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: fengzijian
 * @since: 2020/8/17 16:43
 * @Description:
 */
@Component
public class Venus implements Pattern {

    @Override
    public boolean judge(List<KLineDTO> kLines) {
        return true;
    }
}
