package com.paxaris.authservice.responseDTO;

import java.io.Serializable;

public class UserDetailsRequestDTO implements Serializable {

	private String userName;
	private Integer tenantOrClientId;

	public UserDetailsRequestDTO() {
	}

	public UserDetailsRequestDTO(String userName, Integer tenantOrClientId) {
		this.userName = userName;
		this.tenantOrClientId = tenantOrClientId;
	}

	public String getUserName() {
		return userName;
	}

	public UserDetailsRequestDTO setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public Integer getTenantOrClientId() {
		return tenantOrClientId;
	}

	public UserDetailsRequestDTO setTenantOrClientId(Integer tenantOrClientId) {
		this.tenantOrClientId = tenantOrClientId;
		return this;
	}
}