package backend.API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.API.model.Station;
import backend.API.repository.StationRepository;

@Service
public class StationServiceImpl implements StationService{
	@Autowired
	StationRepository stationRepository;
	@Override
	public List<Station> getAllStations() {
		return stationRepository.findAll();
	}
	@Override
	public Station getStationsbyId(Long stationID) {
		return stationRepository.findById(stationID).get();
	}
	
	

}
