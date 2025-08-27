package io.github.nitishc.grievance.grievance_service.util;

import io.github.nitishc.grievance.grievance_service.dto.GrievanceRequest;
import io.github.nitishc.grievance.grievance_service.dto.GrievanceResponse;
import io.github.nitishc.grievance.grievance_service.model.Grievance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrievancesMapper {

    public GrievanceResponse toDto(Grievance grievance);

    public Grievance toEntity(GrievanceRequest grievanceRequest);
}
