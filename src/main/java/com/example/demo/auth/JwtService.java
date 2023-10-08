package com.example.demo.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService extends JwtContract {

	private static final String SECRET_KEY = "DFatenFSYbaa+PaCOygVv8JtOc3d1UPv2BCIIeQ2TwGTA2EuhNQpGhszoUEN2bFR";
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	// Here we can provide extraClaims
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

		try {

			return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername()) // Subject is unique
				.setIssuer("localhost:9090")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(setTokenLife())
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();

		} catch (SignatureException | ExpiredJwtException e) {
			e.toString();
			return null;
		}
	}

	public String refreshToken(String token) {
		// To Be Implemented
		return null;
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {

		// Does token belongs to the provided userDetails
		System.out.println("JwtService -> isTokenValid()");
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		// From token, returns if it is expired
		return extractSpecificClaim(token, Claims::getExpiration).before(new Date());
	}

	public String extractUsername(String token) {
		// The 'sub' is the 'Subject'
		// (token, claims -> claims.get("sub", String.class)); To Avoid Method Reference
		return extractSpecificClaim(token, Claims::getSubject);
	}

	public Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getSignInKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public <T> T extractSpecificClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Date setTokenLife() {

		// 1000 milisseconds * 30 = 30 seconds
		// 1000 milisseconds * 60 = 1 minute
		// 1000 milisseconds * 60 * 30 = 30 minutes
		// 1000 milisseconds * 60 * 60 = A hour
		// 1000 milisseconds * 60 * 60 * 24 = A day

		System.out.println("JwtService -> setTokenLife() returns -> " + (1000 * 60 * 60 * 24));
		int lifetime = 1000 * 60 * 30; // 30 minutes
        return new Date(System.currentTimeMillis() + lifetime);
	}

}