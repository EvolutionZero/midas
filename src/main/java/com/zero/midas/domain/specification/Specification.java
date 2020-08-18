package com.zero.midas.domain.specification;

import com.zero.midas.domain.entity.kline.KLineNode;

import java.util.List;

/**
 * 形态接口
 *
 * @author: fengzijian
 * @since: 2020/8/17 16:50
 * @Description:
 */
public interface Specification {

    boolean judge(List<KLineNode> kLines);
}