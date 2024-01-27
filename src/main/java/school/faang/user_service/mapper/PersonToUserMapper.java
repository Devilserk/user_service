package school.faang.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import school.faang.user_service.dto.user.person_dto.UserPersonDto;
import school.faang.user_service.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PersonToUserMapper {
    PersonToUserMapper INSTANCE = Mappers.getMapper(PersonToUserMapper.class);

    @Mapping(target = "username", expression = "java(personDto.getFirstName() + personDto.getLastName())")
    @Mapping(target = "email", source = "contactInfo.email")
    @Mapping(target = "phone", source = "contactInfo.phone")
    User toUser(UserPersonDto personDto);

    @Mapping(target = "contactInfo.email", source = "email")
    @Mapping(target = "contactInfo.phone", source = "phone")
    UserPersonDto toUserPersonDto(User user);
}
