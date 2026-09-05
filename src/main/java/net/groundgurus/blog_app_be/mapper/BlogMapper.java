package net.groundgurus.blog_app_be.mapper;

import net.groundgurus.blog_app_be.dto.BlogDTO;
import net.groundgurus.blog_app_be.model.Blog;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper implements Mapper<Blog, BlogDTO> {

  @Override
  public Blog toEntity(BlogDTO dto) {
    return Blog.builder()
        .blogId(dto.getBlogId())
        .title(dto.getTitle())
        .subUrl(dto.getSubUrl())
        .description(dto.getDescription())
        .content(dto.getContent())
        .build();
  }

  @Override
  public BlogDTO toDto(Blog entity) {
    return BlogDTO.builder()
        .blogId(entity.getBlogId())
        .title(entity.getTitle())
        .subUrl(entity.getSubUrl())
        .description(entity.getDescription())
        .content(entity.getContent())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
