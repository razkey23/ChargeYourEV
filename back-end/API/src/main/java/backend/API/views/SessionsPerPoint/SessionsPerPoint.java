package backend.API.views.SessionsPerPoint;


import java.util.List;

public class SessionsPerPoint {
	private String Point;
	private String PointOperator;
	private String RequestTimestamp;
	private String PeriodFrom;
	private String PeriodTo;
	public String getPeriodTo() {
		return PeriodTo;
	}
	public void setPeriodTo(String periodTo) {
		PeriodTo = periodTo;
	}
	private int NumberOfChargingSessions;
	private List<ChargingSession> ChargingSession;
	public String getPoint() {
		return Point;
	}
	public void setPoint(String point) {
		Point = point;
	}
	public String getPointOperator() {
		return PointOperator;
	}
	public void setPointOperator(String pointOperator) {
		PointOperator = pointOperator;
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
	public int getNumberOfChargingSessions() {
		return NumberOfChargingSessions;
	}
	public void setNumberOfChargingSessions(int numberOfChargingSessions) {
		NumberOfChargingSessions = numberOfChargingSessions;
	}
	public List<ChargingSession> getChargingSessionsList() {
		return ChargingSession;
	}
	public void setChargingSessionsList(List<ChargingSession> chargingSessionsList) {
		ChargingSession = chargingSessionsList;
	}
	
	
	
	

}
