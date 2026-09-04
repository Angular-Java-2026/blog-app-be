package net.groundgurus.blog_app_be.controller;

import lombok.RequiredArgsConstructor;
import net.groundgurus.blog_app_be.dto.UserInfoDTO;
import net.groundgurus.blog_app_be.model.UserInfo;
import net.groundgurus.blog_app_be.request.AuthRequest;
import net.groundgurus.blog_app_be.service.UserInfoService;
import net.groundgurus.blog_app_be.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    private final UserInfoService userInfoService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfoDTO userInfo) {
        return userInfoService.addUser(userInfo);
    }

    // Removed the role checks here as they are already managed in SecurityConfig

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return JwtUtils.generateToken(authRequest.getUsername(), jwtSecret, jwtExpirationMs);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
