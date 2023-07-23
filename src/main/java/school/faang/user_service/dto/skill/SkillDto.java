package school.faang.user_service.dto.skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.faang.user_service.exception.DataValidationException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {
    private Long id;
    private String title;

    public void validateSkill() {
        if (this.title.isBlank()) {
            throw new DataValidationException("Title cannot be empty");
        }
    }
}
