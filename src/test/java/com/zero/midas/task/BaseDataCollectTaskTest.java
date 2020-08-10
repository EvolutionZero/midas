package com.zero.midas.task;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: fengzijian
 * @since: 2020/8/10 12:57
 * @Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseDataCollectTaskTest {

    @Autowired
    private BaseDataCollectTask task;

    @Test
    public void daily() {
        task.daily();
    }

    @Test
    public void weekly() {
        task.weekly();
    }

    @Test
    public void monthly() {
        task.monthly();
    }
}