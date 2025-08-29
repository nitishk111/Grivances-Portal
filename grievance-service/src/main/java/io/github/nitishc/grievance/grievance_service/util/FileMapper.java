package io.github.nitishc.grievance.grievance_service.util;

import io.github.nitishc.grievance.grievance_service.dto.FileDto;
import io.github.nitishc.grievance.grievance_service.model.Grievance;
import io.github.nitishc.grievance.grievance_service.model.GrievanceFile;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface FileMapper {


    public GrievanceFile toEntity(MultipartFile fileDto);

    public FileDto toDto(GrievanceFile file);
}
