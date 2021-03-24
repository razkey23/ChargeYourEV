package backend.API.service;

import java.util.List;

import backend.API.model.Station;

public interface StationService {
	List<Station> getAllStations();
	Station getStationsbyId(Long stationID);
}
