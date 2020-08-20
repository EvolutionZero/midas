package com.zero.midas.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.mapper.WeeklyMapper;
import com.zero.midas.model.entity.WeeklyDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeeklyRepository
        extends ServiceImpl<WeeklyMapper, WeeklyDO> {

    public List<KLineNode> listKLine(String code) {
        return baseMapper.listKLine(code);
    }

}