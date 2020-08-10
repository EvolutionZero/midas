package com.zero.midas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.midas.model.entity.StockDO;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper
        extends BaseMapper<StockDO> {
}
