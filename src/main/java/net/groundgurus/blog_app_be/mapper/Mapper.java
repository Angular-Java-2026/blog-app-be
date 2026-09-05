package net.groundgurus.blog_app_be.mapper;

import net.groundgurus.blog_app_be.model.Blog;

public interface Mapper<E, D> {

  E toEntity(D dto);

  D toDto(Blog entity);
}
