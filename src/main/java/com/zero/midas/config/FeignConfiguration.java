package com.zero.midas.config;

import com.zero.midas.remote.AKQJ10Remote;
import com.zero.midas.remote.IFengFinanceRemote;
import com.zero.midas.remote.facade.AKQJ10Facade;
import com.zero.midas.remote.facade.IFengFinanceFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public IFengFinanceFacade iFengFinanceFacade(IFengFinanceRemote client) {
        return new IFengFinanceFacade(client);
    }

    @Bean
    public AKQJ10Facade AKQJ10Facade(AKQJ10Remote client) {
        return new AKQJ10Facade(client);
    }
}
