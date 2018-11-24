package br.ufma.lsdi.service.client;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;

@Configuration
public class FeignConfiguration {

    @Bean
    public SpringEncoder feignEncoder() {
        ObjectFactory<HttpMessageConverters> objectFactory = () ->
                new HttpMessageConverters(new FormHttpMessageConverter());

        return new SpringEncoder(objectFactory);
    }

}
