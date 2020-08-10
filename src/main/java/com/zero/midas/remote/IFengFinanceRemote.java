package com.zero.midas.remote;

import com.zero.midas.config.MidasProperties;
import feign.Logger;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@FeignClient(name = "iFengFinance", url = "http://api.finance.ifeng.com", configuration = {IFengFinanceRemote.Config.class})
public abstract interface IFengFinanceRemote {
    @GetMapping({"/akdaily/?code={code}&type=last"})
    String queryDaily(@PathVariable("code") String paramString);

    @GetMapping({"/akweekly/?code={code}&type=last"})
    String queryWeekly(@PathVariable("code") String paramString);

    @GetMapping({"/akmonthly/?code={code}&type=last"})
    String queryMonthly(@PathVariable("code") String paramString);

    class Config {
        @Autowired
        private MidasProperties midasProperties;

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        public OkHttpClient okHttpClient() {
            return


                    new OkHttpClient().newBuilder().connectTimeout(200L, TimeUnit.MILLISECONDS).readTimeout(200L, TimeUnit.MILLISECONDS).writeTimeout(200L, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).connectionPool(new ConnectionPool(this.midasProperties.getThreshold().intValue(), 200L, TimeUnit.MILLISECONDS)).build();
        }
    }
}
