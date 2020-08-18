package com.zero.midas.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.MonthlyMapper;
import com.zero.midas.model.entity.MonthlyDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonthlyRepository
        extends ServiceImpl<MonthlyMapper, MonthlyDO> {

    public List<MonthlyDO> listByCode(String code) {
        LambdaQueryWrapper<MonthlyDO> conditions = new LambdaQueryWrapper<>();
        conditions.eq(MonthlyDO::getCode, code);
        return list(conditions);
    }

}
