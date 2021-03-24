package backend.API.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="Point")
public class Point {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long PointID;
	
	@Column(name="PointOperator")
	private String PointOperator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "Station_StationID")
	private Station Pstation;
	
	@Column(name="PricingPolicy")
	private String PricingPolicy;
	
	@Column(name="CostPerKwh")
	private float CostPerKwh;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<ChargerType> PointChargerType;
	
	
	public String getPricingPolicy() {
		return PricingPolicy;
	}

	public void setPricingPolicy(String pricingPolicy) {
		PricingPolicy = pricingPolicy;
	}

	public float getCostPerKwh() {
		return CostPerKwh;
	}

	public void setCostPerKwh(float costPerKwh) {
		CostPerKwh = costPerKwh;
	}

	public List<ChargerType> getPointChargerType() {
		return PointChargerType;
	}

	public void setPointChargerType(List<ChargerType> pointChargerType) {
		PointChargerType = pointChargerType;
	}

	public Long getPointID() {
		return PointID;
	}

	public void setPointID(Long pointID) {
		PointID = pointID;
	}

	public String getPointOperator() {
		return PointOperator;
	}

	public void setPointOperator(String pointOperator) {
		PointOperator = pointOperator;
	}

	public Station getPstation() {
		return Pstation;
	}

	public void setPstation(Station pstation) {
		Pstation = pstation;
	}
	
	

}
