package br.ufma.lsdi.service.client;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignInmateConfiguration {

    public static final int TEN_SECONDS = 10000;

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(TEN_SECONDS, TEN_SECONDS);
    }
}
