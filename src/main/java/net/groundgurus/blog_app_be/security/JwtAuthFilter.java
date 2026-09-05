package net.groundgurus.blog_app_be.security;

import static net.groundgurus.blog_app_be.constants.BlogConstants.AUTH;
import static net.groundgurus.blog_app_be.constants.BlogConstants.AUTHORIZATION;
import static net.groundgurus.blog_app_be.constants.BlogConstants.BEARER;
import static net.groundgurus.blog_app_be.constants.BlogConstants.TOKEN_LENGTH;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.groundgurus.blog_app_be.service.UserInfoService;
import net.groundgurus.blog_app_be.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserInfoService userInfoService;
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader(AUTHORIZATION);
    String token = null;
    String username = null;

    if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(BEARER)) {
      token = authHeader.substring(TOKEN_LENGTH).trim();
      try {
        username = jwtUtils.extractUsername(token);
      } catch (Exception e) {
        log.error("Failed to extract username from JWT token: {}", e.getMessage());
      }
    }

    if (StringUtils.isNotBlank(username) && Objects.isNull(
        SecurityContextHolder.getContext().getAuthentication())) {
      try {
        UserDetails userDetails = userInfoService.loadUserByUsername(username);
        if (jwtUtils.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
          log.warn("JWT token validation failed for user: {}", username);
        }
      } catch (Exception e) {
        log.error("Failed to authenticate user from token for {}: {}", username, e.getMessage());
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getServletPath().startsWith(AUTH);
  }
}
