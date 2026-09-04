package net.groundgurus.blog_app_be.repository;

import net.groundgurus.blog_app_be.model.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface BlogRepository extends ListCrudRepository<Blog, Long> {
    @Query("SELECT b from Blog b ORDER BY b.createdAt DESC LIMIT ?1")
    List<Blog> findAll(int max);
    Blog findByBlogId(UUID blogId);
}
