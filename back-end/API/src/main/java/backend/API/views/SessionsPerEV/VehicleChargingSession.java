package backend.API.views.SessionsPerEV;

public class VehicleChargingSession {
	private int SessionIndex;
	private String SessionID;
	private String EnergyProvider;
	private String StartedOn;
	private String FinishedOn;
	private float EnergyDelivered;
	private String PricePolicyRef;
	private float CostPerKwh;
	private float SessionCost;
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
	public String getEnergyProvider() {
		return EnergyProvider;
	}
	public void setEnergyProvider(String energyProvider) {
		EnergyProvider = energyProvider;
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
	public float getEnergyDelivered() {
		return EnergyDelivered;
	}
	public void setEnergyDelivered(float energyDelivered) {
		EnergyDelivered = energyDelivered;
	}
	public String getPricePolicyRef() {
		return PricePolicyRef;
	}
	public void setPricePolicyRef(String pricePolicyRef) {
		PricePolicyRef = pricePolicyRef;
	}
	public float getCostPerKwh() {
		return CostPerKwh;
	}
	public void setCostPerKwh(float costPerKwh) {
		CostPerKwh = costPerKwh;
	}
	public float getSessionCost() {
		return SessionCost;
	}
	public void setSessionCost(float sessionCost) {
		SessionCost = sessionCost;
	}
	
}
