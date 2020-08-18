package com.zero.midas.domain.manager;

import com.zero.midas.domain.entity.collector.IFengCollector;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectorManager {

    @Autowired
    private BeanFactory beanFactory;

    public IFengCollector createIFengCollector(String code) {
        return beanFactory.getBean(IFengCollector.class, code);
    }
}
