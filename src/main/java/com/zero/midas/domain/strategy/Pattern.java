package com.zero.midas.domain.strategy;

import com.zero.midas.domain.model.dto.KLineDTO;

import java.util.List;

/**
 * 形态接口
 *
 * @author: fengzijian
 * @since: 2020/8/17 16:50
 * @Description:
 */
public interface Pattern {

    boolean judge(List<KLineDTO> kLines);
}
