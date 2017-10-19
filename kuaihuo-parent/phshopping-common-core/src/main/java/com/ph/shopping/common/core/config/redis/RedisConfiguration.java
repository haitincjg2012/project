/**  
 * @Title:  RedisConfiguration.java   
 * @Package com.ph.shopping.common.core.config.redis   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月12日 下午4:29:58   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.config.redis;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
/**   
 * @ClassName:  RedisConfiguration   
 * @Description:redis 配置   
 * @author: 李杰
 * @date:   2017年5月12日 下午4:29:58     
 * @Copyright: 2017
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport{
	
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				if (null != params) {
					for (Object obj : params) {
						sb.append(obj.toString());
					}
				}
				return sb.toString();
			}
		};
	}
  
    @Bean  
    public CacheManager cacheManager(  
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {  
        return new RedisCacheManager(redisTemplate);  
    }  
	
	@Bean
	public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}

	@Bean
	public RedisTemplate<String, Object> objRedisTemplate(RedisConnectionFactory connectionFactory,
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
