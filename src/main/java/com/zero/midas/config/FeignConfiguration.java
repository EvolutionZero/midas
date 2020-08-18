package com.zero.midas.config;

import com.zero.midas.remote.IFengFinanceRemote;
import com.zero.midas.remote.facade.IFengFinanceFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public IFengFinanceFacade testRunnerClientFacade(IFengFinanceRemote client) {
        return new IFengFinanceFacade(client);
    }

}
