package school.faang.user_service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import school.faang.user_service.entity.Skill;
import school.faang.user_service.entity.dto.skill.SkillDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillMapper {

    Skill toEntity(SkillDto skillDto);

    SkillDto toDto(Skill skill);
}