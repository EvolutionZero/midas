package com.zero.midas.domain.specification;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.domain.model.dto.CheckResultDTO;

import java.util.List;

/**
 * 检查
 *
 * @author: fengzijian
 * @since: 2020/8/17 16:50
 * @Description:
 */
public interface Checker {

    CheckResultDTO check(List<KLineNode> kLines);

}
