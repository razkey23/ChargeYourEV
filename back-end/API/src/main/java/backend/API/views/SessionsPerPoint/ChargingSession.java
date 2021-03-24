package backend.API.views.SessionsPerPoint;

public class ChargingSession {
	private int SessionIndex;
	private String SessionID;
	private String StartedOn;
	private String FinishedOn;
	private String Protocol;
	private float EnergyDelivered;
	private String Payment;
	private String VehicleType;
	public int getSessionIndex() {
		return SessionIndex;
	}
	public void setSessionIndex(int sessionIndex) {
		SessionIndex = sessionIndex;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	public String getStartedOn() {
		return StartedOn;
	}
	public void setStartedOn(String startedOn) {
		StartedOn = startedOn;
	}
	public String getFinishedOn() {
		return FinishedOn;
	}
	public void setFinishedOn(String finishedOn) {
		FinishedOn = finishedOn;
	}
	public String getProtocol() {
		return Protocol;
	}
	public void setProtocol(String protocol) {
		Protocol = protocol;
	}
	public float getEnergyDelivered() {
		return EnergyDelivered;
	}
	public void setEnergyDelivered(float energyDelivered) {
		EnergyDelivered = energyDelivered;
	}
	public String getPayment() {
		return Payment;
	}
	public void setPayment(String payment) {
		Payment = payment;
	}
	public String getVehicleType() {
		return VehicleType;
	}
	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}
 
}
