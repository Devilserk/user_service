package school.faang.user_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.config.context.UserContext;
import school.faang.user_service.dto.event.GoogleEventResponseDto;
import school.faang.user_service.service.google.GoogleCalendarService;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class GoogleCalendarController {
    private final GoogleCalendarService googleCalendarService;
    private final UserContext userContext;

    @PostMapping("/google/{eventId}")
    public GoogleEventResponseDto createEvent(@PathVariable Long eventId) {
        Long userId = userContext.getUserId();
        try {
            return googleCalendarService.createEvent(userId, eventId);
        } catch (Exception e) {
            log.error("Failed to push event to Google Calendar for user with id:{}\nException: {}",
                    userId, e.getMessage());
            return GoogleEventResponseDto.builder().build();
        }
    }

    @GetMapping("/callback")
    public GoogleEventResponseDto handleCallback(@RequestParam String code, @RequestParam String state) {
        try {
            Long userId = Long.parseLong(state.split("-")[0]);
            Long eventId = Long.parseLong(state.split("-")[1]);
            return googleCalendarService.handleCallback(code, userId, eventId);
        } catch (Exception e) {
            log.error("Failed to push event to Google Calendar\nException: {}", e.getMessage());
            return GoogleEventResponseDto.builder().build();
        }
    }
}