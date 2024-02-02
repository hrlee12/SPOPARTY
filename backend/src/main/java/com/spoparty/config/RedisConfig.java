package com.spoparty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.spoparty.api.party.dto.ChatEnterRequestDto;
import com.spoparty.redis.RedisSubscriber;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer( // (1)
		RedisConnectionFactory connectionFactory,
		MessageListenerAdapter listenerAdapter,
		ChannelTopic channelTopic
	) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, channelTopic);
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) { // (2)
		return new MessageListenerAdapter(subscriber, "onMessage");
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate
		(RedisConnectionFactory connectionFactory) { // (3)
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		return redisTemplate;
	}

	@Bean
	public ChannelTopic channelTopic() { // (4)
		return new ChannelTopic("chat");
	}
}
