package com.bertachiniprojetos.securityJwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;
import com.bertachiniprojetos.data.Vo.V1.security.TokenVO;

import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProvider {
	
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validityInMilliseconds = 3600000; // 1h  
	
	@Autowired
	private UserDetailsService userDetailService;
	
	Algorithm algorithm = null;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenVO createToken(String userName, List<String> roles) {
		Date now = new Date();
		Date validyUntill = new Date(now.getTime() + validityInMilliseconds);
		var acessToken = getAccessToken(userName, roles, now, validyUntill);
		var refreshToken = getAccessToken(userName, roles, now);
		
		return new TokenVO(userName, true, now, validyUntill, acessToken, refreshToken);	
	}

	private String getAccessToken(String userName, List<String> roles, Date now, Date validyUntill) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getAccessToken(String userName, List<String> roles, Date now) {
		// TODO Auto-generated method stub
		return null;
	}

}
