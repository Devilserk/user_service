package school.faang.user_service.filter;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.goal.InvitationFilterDto;
import school.faang.user_service.entity.goal.GoalInvitation;

import java.util.List;
import java.util.stream.Stream;

@Component
public class InvitationInvitedNameFilter implements InvitationFilter {

    @Override
    public boolean isApplicable(InvitationFilterDto filters) {
        return filters.getInvitedNamePattern() != null;
    }

    @Override
    public List<GoalInvitation> apply(Stream<GoalInvitation> invitations, InvitationFilterDto filters) {
        return invitations
                .filter(in -> in.getInvited().getUsername().contains(filters.getInvitedNamePattern()))
                .toList();
    }
}
