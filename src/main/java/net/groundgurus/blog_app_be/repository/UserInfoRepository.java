package net.groundgurus.blog_app_be.repository;

import java.util.Optional;
import net.groundgurus.blog_app_be.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

  Optional<UserInfo> findByEmailOrUsername(String email, String username);
}
