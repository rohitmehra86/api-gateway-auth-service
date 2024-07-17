package com.paxaris.authservice.feignInterface;

import static com.paxaris.authservice.constants.MicroServiceConstants.USER_MNGT_BASE_URL;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paxaris.authservice.constants.MicroServiceConstants.AdminMicroServiceConstants;
import com.paxaris.authservice.responseDTO.UserDTO;
import com.paxaris.authservice.responseDTO.UserDetailsRequestDTO;

@FeignClient(name = "user-service", url = "${zuul.routes.usr-mgt.url}"+USER_MNGT_BASE_URL)
@Service
public interface UserManagementInterface {

	@RequestMapping(value = AdminMicroServiceConstants.FETCH_ADMIN_BY_USERNAME)
	Optional<UserDTO> fetchUserDetails(@RequestBody UserDetailsRequestDTO userDetailsRequestDTO);
}
