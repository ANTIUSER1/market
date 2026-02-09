package pn.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisTPLConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplateOM(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(
                new JdkSerializationRedisSerializer());
        //  new GenericJacksonJsonRedisSerializer(new ObjectMapper()))
        //  ;
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }


}
