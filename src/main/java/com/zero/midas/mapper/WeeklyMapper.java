package com.zero.midas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.model.entity.WeeklyDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyMapper
        extends BaseMapper<WeeklyDO> {

    @Select({"select `code`, date, open, close, high, low, price_ratio, volumn from t_weekly where `code` = #{code} order by date "})
    List<KLineNode> listKLine(@Param("code") String code);
}
