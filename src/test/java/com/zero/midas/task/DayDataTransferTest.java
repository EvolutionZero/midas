package com.zero.midas.task;

import com.zero.midas.model.entity.Daily1DO;
import com.zero.midas.model.entity.StockDO;
import com.zero.midas.repository.Daily1Repository;
import com.zero.midas.repository.StockDayDataRepository;
import com.zero.midas.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: fengzijian
 * @since: 2020/8/10 12:57
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DayDataTransferTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockDayDataRepository stockDayDataRepository;

    @Autowired
    private Daily1Repository daily1Repository;

    @Test
    public void daily() {
        Set<String> codes = stockRepository.list().stream().map(StockDO::getCode).collect(Collectors.toSet());
        int index = 0;
        for (String code : codes) {
            List<Daily1DO> dailyDOS = stockDayDataRepository.listByCode(code).stream().map(day -> {
                Daily1DO dailyDO = new Daily1DO();
                dailyDO.setCode(day.getCode());
                dailyDO.setDate(LocalDate.parse(day.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                dailyDO.setOpen(day.getOpen());
                dailyDO.setClose(day.getClose());
                dailyDO.setHigh(day.getHigh());
                dailyDO.setLow(day.getLow());
                dailyDO.setVolumn(day.getVolumn());
                dailyDO.setTurnover(day.getTurnover());
                dailyDO.setPriceChange(day.getPriceChange());
                dailyDO.setPriceRatio(day.getPriceRatio());
                return dailyDO;
            }).collect(Collectors.toList());
            daily1Repository.saveBatch(dailyDOS);
            System.out.println(code + " -> " + (++index) + "/" + codes.size());
        }
    }


}