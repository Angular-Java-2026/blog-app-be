package net.groundgurus.blog_app_be.service;

import lombok.RequiredArgsConstructor;
import net.groundgurus.blog_app_be.dto.UserInfoDTO;
import net.groundgurus.blog_app_be.model.UserInfo;
import net.groundgurus.blog_app_be.repository.UserInfoRepository;
import net.groundgurus.blog_app_be.security.UserInfoDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

  public static final String USER_ADDED_MESSAGE = "User added successfully!";
  public static final String ROLE_USER = "ROLE_USER";
  private static final String USERNAME_NOT_FOUND_MESSAGE = "User not found with identifier: %s";
  private final UserInfoRepository userInfoRepository;
  private final PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInfo user = userInfoRepository.findByEmailOrUsername(username, username)
        .orElseThrow(() ->
            new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)));
    return new UserInfoDetails(user);
  }

  public String addUser(UserInfoDTO userInfoDTO) {
    String username = StringUtils.isNotBlank(userInfoDTO.getUsername())
        ? userInfoDTO.getUsername()
        : userInfoDTO.getEmail();

    var userInfo = UserInfo.builder()
        .firstName(userInfoDTO.getFirstName())
        .lastName(userInfoDTO.getLastName())
        .username(username)
        .email(userInfoDTO.getEmail())
        .password(encoder.encode(userInfoDTO.getPassword()))
        .roles(ROLE_USER)
        .build();
    userInfoRepository.save(userInfo);
    return USER_ADDED_MESSAGE;
  }
}
