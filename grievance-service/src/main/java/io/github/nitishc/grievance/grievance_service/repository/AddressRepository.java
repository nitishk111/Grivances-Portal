package io.github.nitishc.grievance.grievance_service.repository;

import io.github.nitishc.grievance.grievance_service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
