package com.zero.midas.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.WeeklyMapper;
import com.zero.midas.model.entity.WeeklyDO;
import org.springframework.stereotype.Repository;

@Repository
public class WeeklyRepository
        extends ServiceImpl<WeeklyMapper, WeeklyDO> {
}