package school.faang.user_service.mapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import school.faang.user_service.dto.goal.GoalInvitationDto;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.goal.Goal;
import school.faang.user_service.entity.goal.GoalInvitation;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest
class GoalInvitationMapperTest {
    @Autowired
    GoalInvitationMapper mapper;

    @Test
    public void testToDto() {
        long everyId = 1L;
        GoalInvitation invitation = new GoalInvitation();
        invitation.setId(everyId);
        invitation.setInviter(User.builder().id(everyId).build());
        invitation.setInvited(User.builder().id(everyId).build());
        invitation.setGoal(Goal.builder().id(everyId).build());
        invitation.setStatus(RequestStatus.ACCEPTED);

        GoalInvitationDto result = mapper.toDto(invitation);
        GoalInvitationDto expected = new GoalInvitationDto(everyId, everyId, everyId, everyId, RequestStatus.ACCEPTED);

        assertEquals(expected, result);
    }

    @Test
    public void testToEntity() {
        long everyId = 1L;
        GoalInvitationDto goalInvitationDto = new GoalInvitationDto(everyId, everyId, everyId, everyId, RequestStatus.REJECTED);

        GoalInvitation result = mapper.toEntity(goalInvitationDto);

        assertEquals(everyId, result.getId());
        assertEquals(RequestStatus.REJECTED, result.getStatus());
    }
}