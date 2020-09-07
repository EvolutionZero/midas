package com.zero.midas.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.midas.model.entity.StockBaseInfoDO;
import org.springframework.stereotype.Repository;

@Repository
@DS("slave")
public interface StockBaseInfoMapper
        extends BaseMapper<StockBaseInfoDO> {

}

