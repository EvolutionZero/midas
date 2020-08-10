package com.zero.midas.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.DailyMapper;
import com.zero.midas.model.entity.DailyDO;
import org.springframework.stereotype.Repository;

@Repository
public class DailyRepository
        extends ServiceImpl<DailyMapper, DailyDO> {
}