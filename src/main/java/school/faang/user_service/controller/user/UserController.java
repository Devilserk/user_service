package school.faang.user_service.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.DeactivateResponseDto;
import school.faang.user_service.dto.contact.ExtendedContactDto;
import school.faang.user_service.dto.contact.TgContactDto;
import school.faang.user_service.dto.subscription.UserDto;
import school.faang.user_service.dto.subscription.UserFilterDto;
import school.faang.user_service.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto signup(@RequestBody UserDto userDto) {
        return userService.signup(userDto);
    }

    @GetMapping("/{userId}")
    UserDto getUser(@PathVariable long userId) throws JsonProcessingException {
        return userService.getUserWithPublishProfileViewEvent(userId);
    }

    @GetMapping("/user/{userId}")
    UserDto getUserNoPublish(@PathVariable long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/premium-users")
    public List<UserDto> getPremiumUsers(UserFilterDto userFilterDto) {
        return userService.getPremiumUsers(userFilterDto);
    }

    @GetMapping("/get-by-ids")
    List<UserDto> getUsersByIds(@NotEmpty(message = "ids cannot be empty") List<Long> ids) {
        return userService.getUsersByIds(ids);
    }

    @PostMapping("/deactivation/{userId}")
    public DeactivateResponseDto deactivating(@PathVariable long userId) {
        return userService.deactivateUser(userId);
    }

    @PostMapping("/contacts")
    public void updateUserContact(@RequestBody TgContactDto tgContactDto) {
        userService.updateUserContact(tgContactDto);
    }

    @GetMapping("/{userId}/contacts")
    public ExtendedContactDto getUserContact(@PathVariable Long userId) {
        return userService.getUserContact(userId);
    }

    @GetMapping("/get-by-phone")
    public Long findUserIdByPhoneNumber(@RequestParam(name = "phone") String phoneNumber){
        return userService.findUserIdByPhoneNumber(phoneNumber);
    }

    @GetMapping("/exists/{id}")
    public Boolean checkUserExist(@PathVariable long id) {
        return userService.checkUserExist(id);
    }
}
