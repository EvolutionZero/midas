package com.zero.midas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan({"com.zero.midas.mapper"})
@EnableAsync
@EnableFeignClients
@SpringBootApplication
public class MidasApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MidasApplication.class, args);
    }
}
