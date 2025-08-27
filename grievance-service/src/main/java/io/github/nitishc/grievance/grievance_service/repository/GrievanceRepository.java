package io.github.nitishc.grievance.grievance_service.repository;

import io.github.nitishc.grievance.grievance_service.model.Grievance;
import io.github.nitishc.grievance.grievance_service.util.Department;
import io.github.nitishc.grievance.grievance_service.util.Priority;
import io.github.nitishc.grievance.grievance_service.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrievanceRepository extends JpaRepository<Grievance, Long> {

    public List<Grievance> findAllByUserEmail(String userEmail);

    public List<Grievance> findAllByGrievanceType(Department grievanceType);

    public List<Grievance> findAllByStatus(Status complaintType);

    public List<Grievance> findAllByPriority(Priority priorityType);
}
