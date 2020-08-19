package com.zero.midas.remote;

import com.zero.midas.config.MidasProperties;
import feign.*;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@FeignClient(name = "AKQJ10Remote", configuration = {AKQJ10Remote.Config.class})
public interface AKQJ10Remote {
    @RequestLine("GET /getselfstockinfo.php?jsonp=callback&callback=callback&_=1597768495917")
    String listFollow(URI url, @HeaderMap Map<String, String> headers);


    class Config {
        @Autowired
        private MidasProperties midasProperties;

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        public Contract feignContract() {
            return new feign.Contract.Default();

        }

        @Bean
        public OkHttpClient okHttpClient() {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(200L, TimeUnit.MILLISECONDS)
                    .readTimeout(200L, TimeUnit.MILLISECONDS)
                    .writeTimeout(200L, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .connectionPool(new ConnectionPool(this.midasProperties.getThreshold().intValue(), 200L, TimeUnit.MILLISECONDS))
                    .build();
        }

        @Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                requestTemplate
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                        .header("Accept-Language", "zh-CN,zh;q=0.9");
            };
        }
    }
}
