package com.zero.midas.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.StockMapper;
import com.zero.midas.model.entity.StockDO;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository
        extends ServiceImpl<StockMapper, StockDO> {
}