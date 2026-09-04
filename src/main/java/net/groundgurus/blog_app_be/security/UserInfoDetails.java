package net.groundgurus.blog_app_be.security;

import net.groundgurus.blog_app_be.model.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserInfoDetails implements UserDetails {
    public static final String COMMA = ",";
    private final String username;
    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        this.username = StringUtils.isNotBlank(userInfo.getUsername()) ? userInfo.getUsername() : userInfo.getEmail();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.authorities = StringUtils.isNotBlank(userInfo.getRoles())
                ? Stream.of(userInfo.getRoles().split(COMMA))
                    .map(String::trim)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList())
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
