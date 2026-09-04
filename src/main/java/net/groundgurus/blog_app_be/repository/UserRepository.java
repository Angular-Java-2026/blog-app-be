package net.groundgurus.blog_app_be.repository;

import net.groundgurus.blog_app_be.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);
    Boolean existsByUsername(String username);
}
