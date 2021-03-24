package backend.API.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name="Users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Userid")
	private Long Userid;
	
	
	
	@Column(name="isAdmin")
	private boolean isAdmin;
	
	@Column(name="isProvider")
	public boolean isProvider() {
		return isProvider;
	}

	public void setProvider(boolean isProvider) {
		this.isProvider = isProvider;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Column(name="isProvider")
	private boolean isProvider;
	
	@Column(name="name")
	private String name;
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	

	
	@Column(name="username",unique=true)
	private String username;
	
	@Column(name="password")
	private String password;

	public Long getUserid() {
		return Userid;
	}

	public void setUserid(Long userid) {
		Userid = userid;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
