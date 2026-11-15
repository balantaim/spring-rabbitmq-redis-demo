package com.martinatanasov.consumer.service;

import com.martinatanasov.consumer.model.FatalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class EventRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<FatalMessage> getEventById(String eventId) {
        String key = "event:" + eventId;
        Object event = redisTemplate.opsForValue().get(key);
        if (event instanceof FatalMessage) {
            return Optional.of((FatalMessage) event);
        }
        return Optional.empty();
    }

    public List<FatalMessage> getAllEvents() {
        Set<String> keys = redisTemplate.keys("event:*");
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        return keys.stream()
                .map(redisTemplate.opsForValue()::get)
                .filter(FatalMessage.class::isInstance)
                .map(FatalMessage.class::cast)
                .collect(Collectors.toList());
    }

}
