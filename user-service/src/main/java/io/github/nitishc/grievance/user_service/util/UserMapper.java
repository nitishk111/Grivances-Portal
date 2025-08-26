package io.github.nitishc.grievance.user_service.util;

import io.github.nitishc.grievance.user_service.dto.UserResponse;
import io.github.nitishc.grievance.user_service.dto.UserSignupRequest;
import io.github.nitishc.grievance.user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

    User toEntity(UserSignupRequest request);
}
