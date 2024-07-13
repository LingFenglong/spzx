package com.lingfenglong.spzx.common.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public RedisTemplate<String, JSONObject> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, JSONObject> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<JSONObject> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(JSONObject.class);

        //序列号key value
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
