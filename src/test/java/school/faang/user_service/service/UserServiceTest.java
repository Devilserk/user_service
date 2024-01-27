package school.faang.user_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.contact.ExtendedContactDto;
import school.faang.user_service.dto.contact.TgContactDto;
import school.faang.user_service.dto.user.person_dto.UserPersonDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.entity.contact.Contact;
import school.faang.user_service.entity.contact.ContactType;
import school.faang.user_service.exception.UserNotFoundException;
import school.faang.user_service.mapper.PersonToUserMapper;
import school.faang.user_service.repository.UserCheckRepository;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.contact.ContactRepository;
import school.faang.user_service.util.PasswordGeneration;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCheckRepository userCheckRepository;
    @Mock
    private PersonToUserMapper personToUserMapper;
    @Mock
    private PasswordGeneration passwordGeneration;
    @InjectMocks
    private UserService userService;
    @Mock
    ContactRepository contactRepository;

    private User createUser(UserPersonDto userPersonDto) {
        User user = new User();
        user.setUsername(userPersonDto.getFirstName());
        user.setEmail(userPersonDto.getContactInfo().getEmail());
        user.setPhone(userPersonDto.getContactInfo().getPhone());
        return user;
    }

    @Test
    void testUpdateUserContact_UserNotFound() {
        TgContactDto tgContactDto = TgContactDto.builder().userId(1L).build();
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUserContact(tgContactDto));

        assertEquals("No user found by this id: 1", exception.getMessage());
    }

    @Test
    void testUpdateUserContact_TgContactIsNotPresent() {
        TgContactDto tgContactDto = TgContactDto.builder().userId(1L).tgChatId("anyChatId").build();
        User user = User
                .builder()
                .id(1L)
                .contacts(new ArrayList<>())
                .build();
        Contact contact = Contact.builder().id(1L).user(user).type(ContactType.FACEBOOK).build();
        user.setContacts(List.of(contact));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Contact expected = Contact.builder().id(anyLong()).user(user).type(ContactType.TELEGRAM).contact("anyChatId").build();

        userService.updateUserContact(tgContactDto);
        verify(contactRepository).save(expected);
    }

    @Test
    void testUpdateUserContact_TgContactIsPresent() {
        TgContactDto tgContactDto = TgContactDto.builder().userId(1L).tgChatId("anyChatId").build();
        User user = User
                .builder()
                .id(1L)
                .contacts(new ArrayList<>())
                .build();
        Contact contact = Contact.builder().id(1L).user(user).type(ContactType.TELEGRAM).build();
        user.setContacts(List.of(contact));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Contact expected = Contact.builder().id(1L).user(user).type(ContactType.TELEGRAM).contact("anyChatId").build();

        userService.updateUserContact(tgContactDto);
        verify(contactRepository).save(expected);
    }


    @Test
    void testGetUserContact_UserNotFound() {
        Long userId = 1L;
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUserContact(userId));

        assertEquals("No user found by this id: 1", exception.getMessage());
    }

    @Test
    void testGetUserContact_ContactIsNull() {
        User user = User
                .builder()
                .id(1L)
                .username("anyName")
                .phone("1234567890")
                .contacts(new ArrayList<>())
                .build();
        Contact contact = Contact.builder().id(1L).user(user).type(ContactType.INSTAGRAM).build();
        user.setContacts(List.of(contact));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ExtendedContactDto expected = ExtendedContactDto.builder()
                .userId(1L)
                .username("anyName")
                .phone("1234567890")
                .tgChatId(null)
                .build();
        ExtendedContactDto actual = userService.getUserContact(1L);

        assertEquals(expected, actual);
    }

    @Test
    void testGetUserContact_ContactNotNull() {
        User user = User
                .builder()
                .id(1L)
                .username("anyName")
                .phone("1234567890")
                .contacts(new ArrayList<>())
                .build();
        Contact contact = Contact.builder().id(1L).user(user).type(ContactType.TELEGRAM).contact("anyChatId").build();
        user.setContacts(List.of(contact));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ExtendedContactDto expected = ExtendedContactDto.builder()
                .userId(1L)
                .username("anyName")
                .phone("1234567890")
                .tgChatId("anyChatId")
                .build();
        ExtendedContactDto actual = userService.getUserContact(1L);

        assertEquals(expected, actual);
    }

    @Test
    void testFindUserIdByPhoneNumber_UserNotFound() {
        String phone = "1234567890";
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUserIdByPhoneNumber(phone));

        assertEquals("No user found by this phone: 1234567890", exception.getMessage());
    }
}
