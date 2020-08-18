package com.zero.midas.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: fengzijian
 * @since: 2020/8/18 18:48
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalysisTaskTest {

    @Autowired
    private AnalysisTask analysisTask;

    @Test
    public void exec() {
        analysisTask.exec();
    }
}