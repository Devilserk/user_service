package school.faang.user_service.dto;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.recommendation.SkillRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RecommendationRequestDto {
    private Long id;
    private String message;
    private RequestStatus status;
    private List<SkillRequest> skills;
    private Long requesterId;
    private Long receiverId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}