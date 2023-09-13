package school.faang.user_service.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import school.faang.user_service.dto.redis.EventRecommendationRequestDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecommendationRequestedEventPublisherTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Value("${spring.data.redis.channels.recommendation_requested_event_channel}")
    private String topicRecommendationRequestedEvent;

    @Captor
    private ArgumentCaptor<String> topicCaptor;
    @Captor
    private ArgumentCaptor<EventRecommendationRequestDto> eventCaptor;

    private RecommendationRequestedEventPublisher publisher;

    @Test
    public void testPublish() {
        EventRecommendationRequestDto event = EventRecommendationRequestDto.builder().build();
        redisTemplate.convertAndSend(topicRecommendationRequestedEvent, event);

        verify(redisTemplate).convertAndSend(topicRecommendationRequestedEvent, event);

    }
}