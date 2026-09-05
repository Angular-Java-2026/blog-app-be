package net.groundgurus.blog_app_be.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import net.groundgurus.blog_app_be.config.AppProperties;
import net.groundgurus.blog_app_be.security.UserInfoDetails;
import org.apache.commons.lang3.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

  private final AppProperties appProperties;

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateToken(String username) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expiration = now.plus(appProperties.getJwt().getExpirationMs(),
        ChronoUnit.MILLIS);

    return Jwts.builder()
        .subject(username)
        .issuedAt(DateUtils.convertToDateViaInstant(now))
        .expiration(DateUtils.convertToDateViaInstant(expiration))
        .signWith(getSigningKey())
        .compact();
  }

  public String extractUsername(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public boolean isTokenNotExpired(String token) {
    return extractExpiration(token).after(DateUtils.convertToDateViaInstant(LocalDateTime.now()));
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    if (userDetails instanceof UserInfoDetails userInfoDetails) {
      boolean matches = Strings.CS.equals(username, userInfoDetails.getUsername())
          || Strings.CS.equals(username, userInfoDetails.getEmail());
      return matches && isTokenNotExpired(token);
    }
    return Strings.CS.equals(username, userDetails.getUsername()) && isTokenNotExpired(token);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
