package com.zero.midas.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.midas.model.entity.StockDayDataDO;
import org.springframework.stereotype.Repository;

@Repository
@DS("slave")
public interface StockDayDataMapper
        extends BaseMapper<StockDayDataDO> {

}

