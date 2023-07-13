package school.faang.user_service.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.SkillDto;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.mapper.SkillMapper;
import school.faang.user_service.repository.SkillRepository;

@Service
@Data
@RequiredArgsConstructor

public class SkillService {
    private final SkillRepository skillRepository;

    public SkillDto create(SkillDto skillDto) {
        Skill skillFromDto = SkillMapper.INSTANCE.toEntity(skillDto);
        if (skillRepository.existsByTitle(skillFromDto.getTitle())) {
            throw new DataValidationException("The skill already exists");
        }
        return SkillMapper.INSTANCE.toDTO(skillRepository.save(skillFromDto));
    }
}
