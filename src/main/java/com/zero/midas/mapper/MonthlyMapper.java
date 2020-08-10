package com.zero.midas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zero.midas.model.entity.MonthlyDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MonthlyMapper
        extends BaseMapper<MonthlyDO> {
    @Select({"select date from t_monthly where `code` = #{code} order by date desc limit 1"})
    LocalDate lastTradeDate(@Param("code") String paramString);
}

