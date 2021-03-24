package backend.API.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ChargerType")
public class ChargerType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ChargerID;
	@Column(name="ChargingProtocol")
	private String ChargingProtocol;
	
	public String getChargingProtocol() {
		return ChargingProtocol;
	}

	public void setChargingProtocol(String chargingProtocol) {
		ChargingProtocol = chargingProtocol;
	}

	@Column(name="ChargerName")
	private String ChargerName;
	
	@Column(name="ChargerOutput")
	private float ChargerOutput;

	
	
	public Long getChargerID() {
		return ChargerID;
	}

	public void setChargerID(Long chargerID) {
		ChargerID = chargerID;
	}

	public String getChargerName() {
		return ChargerName;
	}

	public void setChargerName(String chargerName) {
		ChargerName = chargerName;
	}

	public float getChargerOutput() {
		return ChargerOutput;
	}

	public void setChargerOutput(float chargerOutput) {
		ChargerOutput = chargerOutput;
	}
	
	

}
