package net.groundgurus.blog_app_be.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private UUID blogId;
    private String title;
    private String subUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
