package backend.API.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="Vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long VehicleID;
	
	
	@Column(name="VehicleType")
	private String VehicleType;
	
	public String getVehicleType() {
		return VehicleType;
	}

	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "Users_Userid")
	private Users Vusers;

	public Long getVehicleID() {
		return VehicleID;
	}

	public void setVehicleID(Long vehicleID) {
		VehicleID = vehicleID;
	}

	public Users getVusers() {
		return Vusers;
	}

	public void setVusers(Users vusers) {
		Vusers = vusers;
	}


}
