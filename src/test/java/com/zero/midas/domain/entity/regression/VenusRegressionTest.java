package com.zero.midas.domain.entity.regression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: fengzijian
 * @since: 2020/9/1 22:45
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VenusRegressionTest {

    @Autowired
    private VenusRegression venusRegression;

    @Test
    public void daily(){
        venusRegression.daily();
    }
}