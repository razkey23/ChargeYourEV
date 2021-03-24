package backend.API.service;

import java.util.Collection;
import java.util.List;


import backend.API.model.Session;

public interface SessionService {
	List<Session> getAllSessions();
	List<Session> getSessionByPointID(Long pointID);
	List<Session> getSessionsByVehicleID(Long VehicleID);
	List<Session> getSessionsByProviderID(Long ProviderID);
	List<Session> getSessionsByStationID(Long StationID);
	void saveSession(Session ses);
	void deleteSession();
	void saveAllSessions(List<Session> sessionlist);
	Collection<Session> findAllSessions();
	Long getNumberOfAllSessions();

}
