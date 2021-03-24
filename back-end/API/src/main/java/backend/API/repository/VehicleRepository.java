package backend.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import backend.API.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
	@Query(value = "SELECT * FROM softeng20.vehicle Where vusers_userid=?1",nativeQuery = true)
	List<Vehicle> findVehicleByOwnerID(Long OwnerId);
}
