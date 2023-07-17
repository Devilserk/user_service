package school.faang.user_service.service.goal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.repository.goal.GoalRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GoalServiceTest {

    @Mock
    GoalRepository goalRepository;

    @ExtendWith(MockitoExtension.class)
    @InjectMocks
    GoalService service;

    @Test
    public void findGoalsByUserIdLessThanOneTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.findGoalsByUserId(0L);
        });
    }

    @Test
    public void findGoalsByUserIdDataWasCollectedTest() {
        service.findGoalsByUserId(1L);
        Mockito.verify(goalRepository).findGoalsByUserId(Mockito.anyLong());
    }

    @Test
    public void getGoalsByUserLessThanOneTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getGoalsByUser(0L, null);
        });
    }
}