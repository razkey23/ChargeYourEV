package backend.API.views.SessionsPerProvider;

public class ProviderSessionSummary {
	private String ProviderID;
	private String ProviderName;
	private String StationID;
	private int SessionID;
	private String VehicleID;
	private String StartedOn;
	private String FinishedOn;
	private float EnergyDelivered;
	private String PricePolicyRef;
	private float CostPerKwh;
	private float TotalCost;
	public String getProviderID() {
		return ProviderID;
	}
	public void setProviderID(String providerID) {
		ProviderID = providerID;
	}
	public String getProviderName() {
		return ProviderName;
	}
	public void setProviderName(String providerName) {
		ProviderName = providerName;
	}
	public String getStationID() {
		return StationID;
	}
	public void setStationID(String stationID) {
		StationID = stationID;
	}
	public int getSessionID() {
		return SessionID;
	}
	public void setSessionID(int sessionID) {
		SessionID = sessionID;
	}
	public String getVehicleID() {
		return VehicleID;
	}
	public void setVehicleID(String vehicleID) {
		VehicleID = vehicleID;
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
	public float getTotalCost() {
		return TotalCost;
	}
	public void setTotalCost(float totalCost) {
		TotalCost = totalCost;
	}
	
}	
