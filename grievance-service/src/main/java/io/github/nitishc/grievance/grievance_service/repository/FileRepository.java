package io.github.nitishc.grievance.grievance_service.repository;

import io.github.nitishc.grievance.grievance_service.model.GrievanceFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<GrievanceFile, Long> {

//    public GrievanceFile findByGrievanceId(long grievanceId);
}
