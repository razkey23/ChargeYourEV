package backend.API.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Provider")
public class Provider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ProviderID;
	
	@Column(name="ProviderName")
	private String ProviderName;
	
	

	public Long getProviderID() {
		return ProviderID;
	}

	public void setProviderID(Long providerID) {
		ProviderID = providerID;
	}

	public String getProviderName() {
		return ProviderName;
	}

	public void setProviderName(String providerName) {
		ProviderName = providerName;
	}
	
	

}
