package backend.API.service;

import java.util.List;


import backend.API.model.Vehicle;

public interface VehicleService {
	List<Vehicle> getAllVehicles();
	Vehicle getVehicleById(Long VehicleId);
	List<Vehicle> getVehicleByOwnerId(Long OwnerId);
}
