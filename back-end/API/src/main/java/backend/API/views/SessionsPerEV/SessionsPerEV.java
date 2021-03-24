package backend.API.views.SessionsPerEV;

import java.util.List;

public class SessionsPerEV {
	private String VehicleID;
	private String RequestTimestamp;
	private String PeriodFrom;
	private String PeriodTo;
	private float TotalEnergyConsumed;
	private int NumberofVisitedPoints;
	private int NumberOfVehicleChargingSessions;
	private List<VehicleChargingSession> VehicleChargingSessionList;
	
	public String getVehicleID() {
		return VehicleID;
	}
	public void setVehicleID(String vehicleID) {
		VehicleID = vehicleID;
	}
	public String getRequestTimestamp() {
		return RequestTimestamp;
	}
	public void setRequestTimestamp(String requestTimestamp) {
		RequestTimestamp = requestTimestamp;
	}
	public String getPeriodFrom() {
		return PeriodFrom;
	}
	public void setPeriodFrom(String periodFrom) {
		PeriodFrom = periodFrom;
	}
	public String getPeriodTo() {
		return PeriodTo;
	}
	public void setPeriodTo(String periodTo) {
		PeriodTo = periodTo;
	}
	public float getTotalEnergyConsumed() {
		return TotalEnergyConsumed;
	}
	public void setTotalEnergyConsumed(float totalEnergyConsumed) {
		TotalEnergyConsumed = totalEnergyConsumed;
	}
	public int getNumberofVisitedPoints() {
		return NumberofVisitedPoints;
	}
	public void setNumberofVisitedPoints(int numberofVisitedPoints) {
		NumberofVisitedPoints = numberofVisitedPoints;
	}
	public int getNumberOfVehicleChargingSessions() {
		return NumberOfVehicleChargingSessions;
	}
	public void setNumberOfVehicleChargingSessions(int numberOfVehicleChargingSessions) {
		NumberOfVehicleChargingSessions = numberOfVehicleChargingSessions;
	}
	public List<VehicleChargingSession> getVehicleChargingSessionList() {
		return VehicleChargingSessionList;
	}
	public void setVehicleChargingSessionList(List<VehicleChargingSession> vehicleChargingSessionList) {
		VehicleChargingSessionList = vehicleChargingSessionList;
	}
	
}
