package br.ufma.lsdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"br.ufma.lsdi.service"})
@EnableCaching
public class ArLimpoWevApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArLimpoWevApplication.class, args);
    }

}
