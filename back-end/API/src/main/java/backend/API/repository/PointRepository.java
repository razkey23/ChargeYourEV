package backend.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.API.model.Point;


@Repository
public interface PointRepository extends JpaRepository<Point,Long>{
	
	@Query(value = "SELECT * FROM softeng20.point Where pstation_stationid=?1",nativeQuery = true)
	List<Point> findPointsByStationID(Long StationID);
}
