package net.groundgurus.blog_app_be.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {

  private UUID blogId;
  private String title;
  private String subUrl;
  private String description;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
