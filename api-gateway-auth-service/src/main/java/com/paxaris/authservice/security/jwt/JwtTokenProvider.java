package com.paxaris.authservice.security.jwt;

import static com.paxaris.authservice.constants.SecurityConstants.AUTHORIZATION_HEADER;
import static com.paxaris.authservice.constants.SecurityConstants.BEARER_PREFIX;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.paxaris.authservice.constants.ErrorMessage.TokenInvalid;
import com.paxaris.authservice.exceptionHandler.UnauthorisedException;
import com.paxaris.authservice.responseDTO.UserDetailsRequestDTO;
import com.paxaris.authservice.security.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * @author Rohit
 */

@Component
public class JwtTokenProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

	private final JwtProperties jwtProperties;

	private final CustomUserDetailsService userDetailsService;

	private String secretKey;

	public JwtTokenProvider(JwtProperties jwtProperties, CustomUserDetailsService userDetailsService) {
		this.jwtProperties = jwtProperties;
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	protected void init() {
		secretKey = jwtProperties.getSecretKey();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private UserDetailsRequestDTO getUsername(String token) {
		String userName = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		String tenanatId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getAudience();
		return new UserDetailsRequestDTO(userName, Integer.valueOf(tenanatId));
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTHORIZATION_HEADER);

		return (!Objects.isNull(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))
				? bearerToken.substring(7, bearerToken.length())
				: null;
	}

	public boolean validateToken(String token) {

		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

			return (!claims.getBody().getExpiration().before(new Date()));
		} catch (JwtException | IllegalArgumentException e) {
			LOGGER.error("Expired or invalid JWT token");
			throw new UnauthorisedException(TokenInvalid.MESSAGE, TokenInvalid.DEVELOPER_MESSAGE);
		}
	}
}
