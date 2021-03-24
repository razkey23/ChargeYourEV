package backend.API.views.SessionsPerPoint;


public class CsvViewSPPoint {
	private int SessionIndex;
	private String SessionID;
	private String StartedOn;
	private String FinishedOn;
	private String Protocol;
	private float EnergyDelivered;
	private String Payment;
	private String VehicleType;
	private String Point;
	private String PointOperator;
	private String RequestTimestamp;
	private String PeriodFrom;
	private String PeriodTo;
	private int NumberOfChargingSessions;
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
	public String getPeriodTo() {
		return PeriodTo;
	}
	public void setPeriodTo(String periodTo) {
		PeriodTo = periodTo;
	}
	public int getNumberOfChargingSessions() {
		return NumberOfChargingSessions;
	}
	public void setNumberOfChargingSessions(int numberOfChargingSessions) {
		NumberOfChargingSessions = numberOfChargingSessions;
	}


}
