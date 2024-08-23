package com.paxaris.authservice.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.paxaris.authservice.feignInterface.UserManagementInterface;
import com.paxaris.authservice.responseDTO.UserDTO;
import com.paxaris.authservice.responseDTO.UserDetailsRequestDTO;

@Component
public class CustomUserDetailsService {

	private UserManagementInterface userManagementInterface;

	public CustomUserDetailsService(UserManagementInterface adminInterface) {
		this.userManagementInterface = adminInterface;
	}

	public UserDetails loadUserByUsername(UserDetailsRequestDTO userDetailsRequestDTO)
			throws UsernameNotFoundException {
		UserDTO user = userManagementInterface.fetchUserDetails(userDetailsRequestDTO).orElseThrow(
				() -> new UsernameNotFoundException("Username:" + userDetailsRequestDTO.getUserName() + " not found"));

		return new User(userDetailsRequestDTO.getUserName(), user.getPassword(), true, true, true, true,
				getAuthority(user));
	}

	private List<SimpleGrantedAuthority> getAuthority(UserDTO user) {
		return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
}
