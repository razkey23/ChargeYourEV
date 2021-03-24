package backend.API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.API.model.Vehicle;
import backend.API.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService{
	@Autowired
	VehicleRepository vehicleRepository;
	@Override
	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}
	@Override
	public Vehicle getVehicleById(Long VehicleId) {
		// TODO Auto-generated method stub
		return vehicleRepository.findById(VehicleId).get();
	}
	@Override
	public List<Vehicle> getVehicleByOwnerId(Long OwnerId) {
		return vehicleRepository.findVehicleByOwnerID(OwnerId);
	}

}
