package backend.API.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name="Station")
public class Station {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long StationID;
	
	@Column(name="StationOperator")
	private String StationOperator;
	
	@Column(name="Longitude")
	private String StationLongitude;
	
	@Column(name="Latitude")
	private String StationLatitude;
	
	public String getStationLongitude() {
		return StationLongitude;
	}

	public void setStationLongitude(String stationLongitude) {
		StationLongitude = stationLongitude;
	}

	public String getStationLatitude() {
		return StationLatitude;
	}

	public void setStationLatitude(String stationLatitude) {
		StationLatitude = stationLatitude;
	}

	public String getStationName() {
		return StationName;
	}

	public void setStationName(String stationName) {
		StationName = stationName;
	}


	public Provider getEnergyProvider() {
		return EnergyProvider;
	}

	public void setEnergyProvider(Provider energyProvider) {
		EnergyProvider = energyProvider;
	}

	public int getBusyFactor() {
		return BusyFactor;
	}

	public void setBusyFactor(int busyFactor) {
		BusyFactor = busyFactor;
	}
	
	

	@Column(name="StationName")
	private String StationName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "Users_Userid")
	private Provider EnergyProvider;
	
	
	
	@Min(0)
	@Max(5)
	@Column(name="BusyFactor",columnDefinition = "integer default 0")
	private int BusyFactor;
	

	public Long getStationID() {
		return StationID;
	}

	public void setStationID(Long stationID) {
		StationID = stationID;
	}

	public String getStationOperator() {
		return StationOperator;
	}

	public void setStationOperator(String stationOperator) {
		StationOperator = stationOperator;
	}

}
