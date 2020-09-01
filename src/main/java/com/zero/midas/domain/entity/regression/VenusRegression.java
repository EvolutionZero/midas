package com.zero.midas.domain.entity.regression;

import com.zero.midas.domain.specification.Checker;
import com.zero.midas.domain.specification.KLineShape;
import com.zero.midas.domain.specification.impl.check.NotAllowFallChecker;
import com.zero.midas.domain.specification.impl.shape.Venus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: fengzijian
 * @since: 2020/9/1 22:15
 * @Description:
 */
@Component
@Slf4j
public class VenusRegression extends RegressionTemplate {

    @Autowired
    private Venus venus;

    @Autowired
    private NotAllowFallChecker notAllowFallCheck;

    @Override
    protected KLineShape shape() {
        return venus;
    }

    @Override
    protected Checker checker() {
        return notAllowFallCheck;
    }
}
