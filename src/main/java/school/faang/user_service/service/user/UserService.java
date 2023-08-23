package school.faang.user_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.UserProfilePic;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.service.amazon.AvatarService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AvatarService avatarService;
    @Value("${services.dice-bear.url}")
    private String URL;
    @Value("${services.dice-bear.size}")
    private String SIZE;

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        addCreateData(user);

        user =userRepository.save(user);
        return userMapper.toDto(user);
    }

    public User findUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new DataValidationException("User was not found"));
    }

    public boolean areOwnedSkills(long userId, List<Long> skillIds) {
        if (skillIds.isEmpty()) {
            return true;
        }
        return userRepository.countOwnedSkills(userId, skillIds) == skillIds.size();
    }

    public UserDto getUser(long id) {
        return userMapper.toDto(findUserById(id));
    }

    public List<UserDto> getUsersByIds(List<Long> ids) {
        List<User> users = StreamSupport.stream(userRepository.findAllById(ids).spliterator(), false).toList();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    private void addCreateData(User user) {
        user.setCreatedAt(LocalDateTime.now());
        UserProfilePic userProfilePic = UserProfilePic.builder()
                .name(user.getUsername()+user.getId())
                .build();

        createDiceBearAvatar(userProfilePic);
        user.setUserProfilePic(userProfilePic);
    }

    private void createDiceBearAvatar(UserProfilePic userProfilePic) {
        userProfilePic.setFileId(URL + userProfilePic.getName());
        userProfilePic.setSmallFileId(URL + userProfilePic.getName()+"&" + SIZE);

        avatarService.saveToAmazonS3(userProfilePic);
    }
}