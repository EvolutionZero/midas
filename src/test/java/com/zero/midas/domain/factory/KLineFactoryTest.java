package com.zero.midas.domain.factory;

import com.zero.midas.domain.entity.kline.KLine;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author: fengzijian
 * @since: 2020/8/17 16:58
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KLineFactoryTest {

    @Autowired
    private KLineFactory kLineFactory;

    @Test
    public void getKLine() {
        KLine kLine = kLineFactory.getKLine(Lists.newArrayList());
        System.out.println(kLine.isVenus());
    }
}