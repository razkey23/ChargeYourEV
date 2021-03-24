package backend.API.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.API.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
	@Query(value = "SELECT * FROM softeng20.session Where svehicle_vehicleid=?1 ORDER BY connection_time",nativeQuery = true)
	List<Session> findSessionsByVehicledID(Long Vehicleid);
	
	@Query(value = "SELECT * FROM softeng20.session Where spoint_pointid=?1 ORDER BY connection_time",nativeQuery = true)
	List<Session> findSessionsByPointid(Long pointid);
	
	//"SELECT * FROM session WHERE spoint_pointid IN (SELECT pointid  FROM point WHERE pstation_stationid=?1)";
	@Query(value = "SELECT * FROM session WHERE spoint_pointid IN (SELECT pointid  FROM point WHERE pstation_stationid=?1 ORDER BY spoint_pointid,connection_time",nativeQuery = true)
	List<Session> findSessionsByStationID(Long stationID);
	
	@Query(value = "SELECT COUNT(*) FROM session",nativeQuery=true)
	Long getNumberOfAllSessions();
	/*@Query(value = "DELETE * FROM softeng20.session",nativeQuery = true)
	void deleteSessions(); */
	
	@Query(value="SELECT * FROM session where spoint_pointid IN (SELECT pointid FROM point WHERE pstation_stationid IN ( SELECT stationid FROM station WHERE energy_provider_providerid=?1)) ORDER BY connection_time",nativeQuery=true)
	List<Session> findSessionsByProviderID(Long ProviderID);
		
}
