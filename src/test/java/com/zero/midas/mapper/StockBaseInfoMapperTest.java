package com.zero.midas.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zero.midas.model.entity.StockBaseInfoDO;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: fengzijian
 * @since: 2020/9/7 23:36
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockBaseInfoMapperTest {

    @Autowired
    private StockBaseInfoMapper stockBaseInfoMapper;

    @Autowired
    private StockRepository stockRepository;


    @Test
    public void test() {
        List<StockBaseInfoDO> stockBaseInfoDOS = stockBaseInfoMapper.selectList(new QueryWrapper<>());
        stockBaseInfoDOS.forEach(s -> System.out.println(s));
        List<String> codes = stockBaseInfoDOS.stream().map(stock -> (stock.getExchange().equals("上海") ? "sh" : "sz") + stock.getCode()).collect(Collectors.toList());
        List<StockDO> stocks = stockRepository.list();
        codes.removeAll(stocks.stream().map(StockDO::getCode).collect(Collectors.toSet()));
        System.out.println(codes);
        System.out.println(codes.size());
    }
}