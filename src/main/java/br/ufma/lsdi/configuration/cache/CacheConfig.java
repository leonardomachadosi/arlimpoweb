package br.ufma.lsdi.configuration.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> cacheList = new ArrayList<Cache>();
        cacheList.add(new ConcurrentMapCache("NaturezaCustodia"));
//        cacheList.add(new ConcurrentMapCache("LeiOrigem"));
//        cacheList.add(new ConcurrentMapCache("LeiInciso"));
//        cacheList.add(new ConcurrentMapCache("IncidenciaPenal"));
//        cacheList.add(new ConcurrentMapCache("LeiParagrafo"));
        cacheList.add(new ConcurrentMapCache("Municipios"));
        cacheList.add(new ConcurrentMapCache("Pais"));
        cacheList.add(new ConcurrentMapCache("GetByCodPessoa"));
        cacheList.add(new ConcurrentMapCache("DetentoDadoFisico"));
        cacheList.add(new ConcurrentMapCache("Medicamentos"));


        //so like that you can create as many as you want
        cacheManager.setCaches(cacheList);
        return cacheManager;
    }
}