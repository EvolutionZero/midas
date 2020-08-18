package com.zero.midas.domain.manager;

import com.zero.midas.domain.entity.collector.IFengCollector;
import com.zero.midas.domain.factory.IFengCollectorFactory;
import com.zero.midas.remote.facade.IFengFinanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectorManager {
    @Autowired
    private IFengCollectorFactory iFengCollectorFactory;
    @Autowired
    private IFengFinanceFacade iFengFinanceFacade;

    public IFengCollector createIFengCollector(String code) {
        return new IFengCollector(code, iFengFinanceFacade, this.iFengCollectorFactory);
    }
}
