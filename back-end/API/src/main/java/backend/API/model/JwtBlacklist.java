package backend.API.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JwtBlacklist")
public class JwtBlacklist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long JwtId;
	
	
	@Column(name="jwt",unique=true)
	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public Long getJwtId() {
		return JwtId;
	}

	public void setJwtId(Long jwtId) {
		JwtId = jwtId;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	

}
