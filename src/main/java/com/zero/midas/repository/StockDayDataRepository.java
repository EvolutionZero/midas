package com.zero.midas.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.StockDayDataMapper;
import com.zero.midas.model.entity.StockDayDataDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockDayDataRepository
        extends ServiceImpl<StockDayDataMapper, StockDayDataDO> {

    public List<StockDayDataDO> listByCode(String code) {
        QueryWrapper<StockDayDataDO> conditions = new QueryWrapper<>();
        conditions.eq("Code", code.replace("sh", "").replace("sz", ""));
        conditions.orderByAsc("StatDate");

        return baseMapper.selectList(conditions);
    }

}