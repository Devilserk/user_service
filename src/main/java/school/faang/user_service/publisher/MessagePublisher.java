package school.faang.user_service.publisher;

public interface MessagePublisher {
    void publish(String topic, String message);
}