//package com.example.itmonster.configuration;
//
//import java.util.Objects;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.config.CacheConfiguration;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//
//    /*
//        - Eh캐시 매니저 생성 도우미
//          빈등록을 해놓으면 된다.
//    */
//    @Bean
//    public EhCacheManagerFactoryBean cacheManagerFactoryBean(){
//        return new EhCacheManagerFactoryBean();
//    }
//
//    /*
//        - EhcacheManager 등록
//    */
//    @Bean
//    public EhCacheCacheManager ehCacheCacheManager(){
//
//        // 캐시 설정
//        CacheConfiguration layoutCacheConfiguration = new CacheConfiguration()
//            .eternal(false)
//            .timeToIdleSeconds(0)
//            .timeToLiveSeconds(0)
//            .maxEntriesLocalHeap(0)
//            .memoryStoreEvictionPolicy("LRU")
//            .name("layoutCaching");
//// 설정을 가지고 캐시 생성
//        Cache layoutCache = new net.sf.ehcache.Cache(layoutCacheConfiguration);
//
//        // 캐시 팩토리에 생성한 eh캐시를 추가
//        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(layoutCache);
//        // 캐시 팩토리를 넘겨서 eh캐시 매니저 생성
//        return new EhCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean().getObject()));
//    }
//}
