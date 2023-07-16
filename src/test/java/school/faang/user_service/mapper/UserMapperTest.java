package school.faang.user_service.mapper;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.entity.Country;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.goal.Goal;
import school.faang.user_service.entity.recommendation.Recommendation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    private UserMapper userMapper;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();

        user = User.builder()
                .id(1L)
                .username("username")
                .email("user@email")
                .phone("12345678")
                .aboutMe("aboutUser")
                .country(Country.builder().title("country").build())
                .city("city")
                .experience(10)
                .followers(List.of(User.builder().id(2L).build()))
                .followees(List.of(User.builder().id(3L).build()))
                .mentees(List.of(User.builder().id(4L).build()))
                .mentors(List.of(User.builder().id(5L).build()))
                .goals(List.of(Goal.builder().id(1L).build()))
                .skills(List.of(Skill.builder().id(1L).build()))
                .recommendationsGiven(List.of(Recommendation.builder().id(1L).build()))
                .recommendationsReceived(List.of(Recommendation.builder().id(2L).build()))
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .username("username")
                .email("user@email")
                .phone("12345678")
                .aboutMe("aboutUser")
                .city("city")
                .experience(10)
                .build();
    }

    @Test
    void userToUserDto_shouldMatchAllFields() {
        UserDto result = userMapper.userToUserDto(user);
        assertAll(() -> {
            assertEquals(1L, result.getId());
            assertEquals("username", result.getUsername());
            assertEquals("user@email", result.getEmail());
            assertEquals("12345678", result.getPhone());
            assertEquals("aboutUser", result.getAboutMe());
            assertEquals("country", result.getCountry());
            assertEquals("city", result.getCity());
            assertEquals(10, result.getExperience());
            assertEquals(List.of(2L), result.getFollowerIds());
            assertEquals(List.of(3L), result.getFolloweeIds());
            assertEquals(List.of(4L), result.getMenteeIds());
            assertEquals(List.of(5L), result.getMentorIds());
            assertEquals(List.of(1L), result.getGoalIds());
            assertEquals(List.of(1L), result.getSkillIds());
            assertEquals(List.of(1L), result.getRecommendationGivenIds());
            assertEquals(List.of(2L), result.getRecommendationReceivedIds());
        });
    }

    @Test
    void userDtoToUser_shouldMatchAllFields() {
        User result = userMapper.userDtoToUser(userDto);
        assertAll(() -> {
            assertEquals(1L, result.getId());
            assertEquals("username", result.getUsername());
            assertEquals("user@email", result.getEmail());
            assertEquals("12345678", result.getPhone());
            assertEquals("aboutUser", result.getAboutMe());
            assertEquals("city", result.getCity());
            assertEquals(10, result.getExperience());
        });
    }
}