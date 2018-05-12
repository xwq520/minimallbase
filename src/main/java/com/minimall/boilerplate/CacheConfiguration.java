package com.minimall.boilerplate;

import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Title: .
 * <p>Description: </p>

 */
@Configuration
public class CacheConfiguration {

  @Bean
  public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
    EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();

    factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
    factoryBean.setShared(true);
    factoryBean.setAcceptExisting(true);

    return factoryBean;
  }

  @Bean
  public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

/*  @Bean
  public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, User> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }*/

  @Bean
  public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

/*  @Bean
  public RedisTemplate<String, List<Department>> departmentsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, List<Department>> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }*/
}
