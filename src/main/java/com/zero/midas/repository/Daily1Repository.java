package com.zero.midas.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.midas.mapper.Daily1Mapper;
import com.zero.midas.model.entity.Daily1DO;
import org.springframework.stereotype.Repository;

@Repository
public class Daily1Repository
        extends ServiceImpl<Daily1Mapper, Daily1DO> {

}