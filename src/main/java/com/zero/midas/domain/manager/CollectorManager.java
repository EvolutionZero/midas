package com.zero.midas.domain.manager;

import com.zero.midas.domain.entity.collector.IFengCollector;
import com.zero.midas.domain.factory.IFengCollectorFactory;
import com.zero.midas.remote.IFengFinanceRemote;
import com.zero.midas.remote.facade.IFengFinanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectorManager {
    @Autowired
    private IFengFinanceRemote iFengFinanceRemote;
    @Autowired
    private IFengCollectorFactory iFengCollectorFactory;

    public IFengCollector createIFengCollector(String code) {
        return new IFengCollector(code, new IFengFinanceFacade(this.iFengFinanceRemote), this.iFengCollectorFactory);
    }
}
