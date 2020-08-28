package com.zero.midas.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: fengzijian
 * @since: 2020/8/28 17:23
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckTaskTest {

    @Autowired
    private CheckTask checkTask;

    @Test
    public void analysisMonthly() {
        checkTask.analysisMonthly();
    }

    @Test
    public void analysisDaily() {
        checkTask.analysisDaily();
    }
}