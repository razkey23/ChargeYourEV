package backend.API.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Session")
public class Session {
	
	
	public Long getSessionID() {
		return SessionID;
	}

	public void setSessionID(Long sessionID) {
		SessionID = sessionID;
	}

	public float getEnergyDelivered() {
		return EnergyDelivered;
	}

	public void setEnergyDelivered(float energyDelivered) {
		EnergyDelivered = energyDelivered;
	}

	public Timestamp getConnectionTime() {
		return ConnectionTime;
	}

	public void setConnectionTime(Timestamp connectionTime) {
		ConnectionTime = connectionTime;
	}

	public Timestamp getDisconnectTime() {
		return DisconnectTime;
	}

	public void setDisconnectTime(Timestamp disconnectTime) {
		DisconnectTime = disconnectTime;
	}

	public Timestamp getDoneChargingTime() {
		return DoneChargingTime;
	}

	public void setDoneChargingTime(Timestamp doneChargingTime) {
		DoneChargingTime = doneChargingTime;
	}

	

	public Vehicle getSVehicle() {
		return SVehicle;
	}

	public void setSVehicle(Vehicle sVehicle) {
		SVehicle = sVehicle;
	}

	public Point getSPoint() {
		return SPoint;
	}

	public void setSPoint(Point sPoint) {
		SPoint = sPoint;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long SessionID;
	
	
	public float getCost_per_kwh() {
		return cost_per_kwh;
	}

	public void setCost_per_kwh(float cost_per_kwh) {
		this.cost_per_kwh = cost_per_kwh;
	}

	@Column (name ="cost_per_kwh")
	private float cost_per_kwh;
	
	@Column(name="EnergyDelivered")
	private float EnergyDelivered;
	
	@Column(name="ConnectionTime")
	private Timestamp ConnectionTime;

	@Column(name="DisconnectTime")
	private Timestamp DisconnectTime;
	
	@Column(name="DoneChargingTime")
	private Timestamp DoneChargingTime;
	
	@Column(name="PricePolicyRef")
	private String PricePolicyRef;
	
	@Column(name="Protocol")
	private String Protocol;
	
	@Column(name="Payment")
	private String Payment;
	
	
	
	
	public String getPricePolicyRef() {
		return PricePolicyRef;
	}

	public void setPricePolicyRef(String pricePolicyRef) {
		PricePolicyRef = pricePolicyRef;
	}

	public String getProtocol() {
		return Protocol;
	}

	public void setProtocol(String protocol) {
		this.Protocol = protocol;
	}

	public String getPayment() {
		return Payment;
	}

	public void setPayment(String payment) {
		Payment = payment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "Station_StationID")
	private Vehicle SVehicle;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "Point_PointID")
	//@JoinColumn(name = "Station_StationID")
	private Point SPoint;
	
	
}
