package backend.API.views.SessionsPerStation;


public class SessionSummary {
	private String PointID;
	private int PointSessions;
	private float EnergyDelivered;
	public String getPointID() {
		return PointID;
	}
	public void setPointID(String pointID) {
		PointID = pointID;
	}
	public int getPointSessions() {
		return PointSessions;
	}
	public void setPointSessions(int pointSessions) {
		PointSessions = pointSessions;
	}
	public float getEnergyDelivered() {
		return EnergyDelivered;
	}
	public void setEnergyDelivered(float energyDelivered) {
		EnergyDelivered = energyDelivered;
	}
	

}
