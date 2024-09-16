package edu.ntnu.idatt2105.calculator.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  // 512 bits encryption key.
  private static final String SECRET_KEY = "7c69211f63161611be78b3d7a986d873608953a07eece162e80b90adaf7a02f75f34b54e7ef158bbff58a0de78d2ce415d24b85da67d210b9fcb1a48c2fe4352";

  /**
   * Extract username from JWT token.
   *
   * @param token The JWT token to extract the username from.
   * @return The username associated with the token.
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from the JWT token.
   *
   * @param token The JWT token to extract the claim from.
   * @param claimsResolver The function to resolve the claim from the extracted JWT claims.
   * @param <T> The type of the claim to extract.
   * @return The extracted claim.
   */
  public <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> extraClaims = new HashMap<>();
    return generateToken(extraClaims, userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, @NonNull UserDetails userDetails) {
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // The Expiration date is 5 min from the current time.
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all the claims from the given JWT token.
   *
   * @param token The JWT token from which the claims are extracted.
   * @return A {@link Claims} object containing all the claims form the token.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  /**
   * Retrieves the signing key used to sign JWT tokens.
   *
   * @return A {@link Key} object representing the signing key.
   */
  private Key getSigningKey() {
    byte[] key = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(key);
  }
}
