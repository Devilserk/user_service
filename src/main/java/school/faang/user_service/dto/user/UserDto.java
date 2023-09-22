package school.faang.user_service.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.faang.user_service.entity.contact.PreferredContact;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Информация о пользователе")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @Min(1L)
    @Max(Long.MAX_VALUE)
    @Schema(description = "Идентификатор пользователя")
    private Long id;
    @Schema(description = "Имя")
    private String username;
    @Email
    @Schema(description = "Email")
    private String email;
    @Schema(description = "Телефон")
    private String phone;
    @Schema(description = "Информация о пользователе")
    private String aboutMe;
    @Schema(description = "Страна")
    private String country;
    @Schema(description = "Город")
    private String city;
    @Schema(description = "Опыт")
    private Integer experience;
    @Schema(description = "Предпочитаемый тип связи")
    private PreferredContact preference;
}