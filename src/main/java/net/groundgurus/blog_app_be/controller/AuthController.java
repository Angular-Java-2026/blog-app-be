package net.groundgurus.blog_app_be.controller;

import lombok.RequiredArgsConstructor;
import net.groundgurus.blog_app_be.dto.UserInfoDTO;
import net.groundgurus.blog_app_be.request.AuthRequest;
import net.groundgurus.blog_app_be.response.AuthResponse;
import net.groundgurus.blog_app_be.service.UserInfoService;
import net.groundgurus.blog_app_be.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.groundgurus.blog_app_be.constants.BlogConstants.AUTHENTICATION_ERROR_MSG;
import static net.groundgurus.blog_app_be.constants.BlogConstants.INVALID_USER_REQUEST;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserInfoService userInfoService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/addUser")
    public ResponseEntity<AuthResponse> addNewUser(@RequestBody UserInfoDTO userInfo) {
        String responseMessage = userInfoService.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(responseMessage));
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtils.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException(INVALID_USER_REQUEST);
        }
    }

    @ExceptionHandler({AuthenticationException.class, UsernameNotFoundException.class})
    public ResponseEntity<AuthResponse> handleAuthenticationException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(AUTHENTICATION_ERROR_MSG));
    }
}
