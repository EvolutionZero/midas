package com.zero.midas.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.MonthlyMapper;
import com.zero.midas.model.entity.MonthlyDO;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyRepository
        extends ServiceImpl<MonthlyMapper, MonthlyDO> {
}
