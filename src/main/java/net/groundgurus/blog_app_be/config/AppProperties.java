package net.groundgurus.blog_app_be.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private Jwt jwt = new Jwt();
  private Cors cors = new Cors();

  @Getter
  @Setter
  public static class Jwt {

    private String secret;
    private long expirationMs;
  }

  @Getter
  @Setter
  public static class Cors {

    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private boolean allowCredentials;
  }
}
