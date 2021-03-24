package backend.API.service;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.API.model.Session;
import backend.API.repository.SessionRepository;
import backend.API.repository.PointRepository;


@Service
public class SessionServiceImpl implements SessionService {
	@Autowired 
	SessionRepository sessionRepository;
	@Autowired
	PointRepository pointRepository;
	
	@Override
	public List<Session> getAllSessions() {
		return sessionRepository.findAll();
	}
	
	public List<Session> getSessionByPointID(Long pointID){
		List<Session> temp=sessionRepository.findSessionsByPointid(pointID);
		/*List<Session> temp=sessionRepository.findAll();
		List<Session> res= new ArrayList<Session>();
		for (int i=0;i<temp.size();i++) {
			Session curr=temp.get(i);
			if (curr.getSPoint().getPointID()==pointID) {
				res.add(curr);
			}	
		} */
		return temp;
	}

	@Override
	public List<Session> getSessionsByVehicleID(Long VehicleID) {
		return sessionRepository.findSessionsByVehicledID(VehicleID);
	}
	
	
	@Override
	public List<Session> getSessionsByProviderID(Long ProviderID) {
		return sessionRepository.findSessionsByProviderID(ProviderID);
	}

	@Override
	public Collection<Session> findAllSessions() {
		return sessionRepository.findAll();
	}

	@Override
	public List<Session> getSessionsByStationID(Long StationID) {
		return sessionRepository.findSessionsByStationID(StationID);
	}

	@Override
	public void saveSession(Session ses) {
		//sessionRepository.save
		// TODO Auto-generated method stub
		sessionRepository.save(ses);
	}
	
	

	@Override
	public void deleteSession() {
		sessionRepository.deleteAll();
		//sessionRepository.deleteSessions();	
	}

	@Override
	public void saveAllSessions(List<Session> sessionlist) {
		sessionRepository.saveAll(sessionlist);
	}

	@Override
	public Long getNumberOfAllSessions() {
		return sessionRepository.getNumberOfAllSessions();
	}

}
