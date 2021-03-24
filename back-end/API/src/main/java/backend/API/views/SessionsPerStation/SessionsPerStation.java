package backend.API.views.SessionsPerStation;

import java.util.List;

public class SessionsPerStation {
	private String StationID;
	private String Operator;
	private String RequestTimestamp;
	private String PeriodFrom;
	private String PeriodTo;
	private float TotalEnergyDelivered;
	private int NumberOfChargingSessions;
	private int NumberOfActivePoints;
	private List<SessionSummary> SessionsSummary;
	public String getStationID() {
		return StationID;
	}
	public void setStationID(String stationID) {
		StationID = stationID;
	}
	public String getOperator() {
		return Operator;
	}
	public void setOperator(String operator) {
		Operator = operator;
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
	public float getTotalEnergyDelivered() {
		return TotalEnergyDelivered;
	}
	public void setTotalEnergyDelivered(float totalEnergyDelivered) {
		TotalEnergyDelivered = totalEnergyDelivered;
	}
	public int getNumberOfChargingSessions() {
		return NumberOfChargingSessions;
	}
	public void setNumberOfChargingSessions(int numberOfChargingSessions) {
		NumberOfChargingSessions = numberOfChargingSessions;
	}
	public int getNumberOfActivePoints() {
		return NumberOfActivePoints;
	}
	public void setNumberOfActivePoints(int numberOfActivePoints) {
		NumberOfActivePoints = numberOfActivePoints;
	}
	public List<SessionSummary> getSessionsSummary() {
		return SessionsSummary;
	}
	public void setSessionsSummary(List<SessionSummary> sessionsSummary) {
		SessionsSummary = sessionsSummary;
	}
	
	

}
