package io.github.nitishc.grievance.user_service.util;

import io.github.nitishc.grievance.user_service.dto.OfficerResponse;
import io.github.nitishc.grievance.user_service.dto.OfficerSignupRequest;
import io.github.nitishc.grievance.user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfficerMapper {

    public OfficerResponse toDto(User user);

    public User toEntity(OfficerSignupRequest request);
}
