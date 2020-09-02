package com.zero.midas.domain.specification.impl.shape;

import com.zero.midas.domain.entity.kline.KLineNode;
import com.zero.midas.repository.DailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author: fengzijian
 * @since: 2020/9/2 12:25
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class VenusTest {

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private Venus venus;

    @Test
    public void judge() {
        String code = "";
        LocalDate date = LocalDate.of(2020, 9, 2);
        List<KLineNode> kLineNodes = dailyRepository.listKLine(code)
                .stream()
                .filter(node -> node.getDate().isBefore(date) || node.getDate().isEqual(date))
                .collect(Collectors.toList());
        venus.judge(kLineNodes);
    }
}