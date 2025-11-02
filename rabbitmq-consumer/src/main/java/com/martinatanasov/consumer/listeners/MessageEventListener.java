package com.martinatanasov.consumer.listeners;

import com.martinatanasov.consumer.model.FatalMessage;
import com.martinatanasov.consumer.services.EventRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class MessageEventListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final EventRedisService eventRedisService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleMessage(FatalMessage event) {
        log.info("\n\tReceived event: \n {}", event);
        // Save the event to Redis using event ID as key
        redisTemplate.opsForValue().set("event:" + event.id(), event);
        //Get all events
        log.info("Get all events\n {}", getAllEvents());
        //Get already stored event
        log.info("Get stored event\n {}", getEventById(event.id()));
    }

    private FatalMessage getEventById(String eventId) {
        Optional<FatalMessage> data = eventRedisService.getEventById(eventId);
        return data.orElse(null);
    }

    private List<FatalMessage> getAllEvents() {
        final List<FatalMessage> events = eventRedisService.getAllEvents();
        log.info("Data: {}", events);
        return events;
    }

}
