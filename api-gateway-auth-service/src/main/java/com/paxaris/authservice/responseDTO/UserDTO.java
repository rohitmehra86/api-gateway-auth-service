package com.paxaris.authservice.responseDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Rohit
 */

@Getter
@Setter
public class UserDTO implements Serializable{

	private Integer userId;

	private String fullName;

	private String gender;

	private String userName;
	private String password;
	private String status;
	
	private String email;

	private Set<Role> roles = new HashSet<>();
}
