package net.groundgurus.blog_app_be.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Function;

public class JwtUtils {
    private static SecretKey getSigningKey(String jwtSecret) {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String username, String jwtSecret, long jwtExpirationMs) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusSeconds(jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(DateUtils.convertToDateViaInstant(now))
                .expiration(DateUtils.convertToDateViaInstant(expiration))
                .signWith(getSigningKey(jwtSecret))
                .compact();
    }

    public static String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(DateUtils.convertToDateViaInstant(LocalDateTime.now()));
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return StringUtils.equals(username, userDetails.getUsername()) && !isTokenExpired(token);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey(token))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
