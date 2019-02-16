package com.kapil.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapil.redis.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan("com.kapil.redis")
@EnableRedisRepositories(basePackages = "com/kapil/redis/entity")
@PropertySource("classpath:application.properties")
public class SpringConfigBeanRedis {



    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());


        return template;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(12000);
        return jedisConFactory;
    }


    @WritingConverter
    public class AddressToBytesConverter implements Converter<Role, byte[]> {

        private final Jackson2JsonRedisSerializer<Role> serializer;

        public AddressToBytesConverter() {

            serializer = new Jackson2JsonRedisSerializer<Role>(Role.class);
            serializer.setObjectMapper(new ObjectMapper());
        }

        @Override
        public byte[] convert(Role value) {
            return serializer.serialize(value);
        }
    }

    @ReadingConverter
    public class BytesToAddressConverter implements Converter<byte[], Role> {

        private final Jackson2JsonRedisSerializer<Role> serializer;

        public BytesToAddressConverter() {

            serializer = new Jackson2JsonRedisSerializer<Role>(Role.class);
            serializer.setObjectMapper(new ObjectMapper());
        }

        @Override
        public Role convert(byte[] value) {
            return serializer.deserialize(value);
        }
    }

}
