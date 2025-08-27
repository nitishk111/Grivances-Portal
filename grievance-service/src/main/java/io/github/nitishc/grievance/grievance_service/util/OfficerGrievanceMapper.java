package io.github.nitishc.grievance.grievance_service.util;

import io.github.nitishc.grievance.grievance_service.dto.OfficerGrievanceResponse;
import io.github.nitishc.grievance.grievance_service.model.Grievance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfficerGrievanceMapper {

    OfficerGrievanceResponse toDto(Grievance grievance);
}
