package net.groundgurus.blog_app_be.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.groundgurus.blog_app_be.dto.BlogDTO;
import net.groundgurus.blog_app_be.mapper.BlogMapper;
import net.groundgurus.blog_app_be.model.Blog;
import net.groundgurus.blog_app_be.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

  private final BlogService blogService;
  private final BlogMapper blogMapper;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody BlogDTO blogDTO) {
    blogService.createBlog(blogDTO);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{blogId}")
  public ResponseEntity<BlogDTO> get(@PathVariable("blogId") UUID blogId) {
    Optional<Blog> blog = blogService.retrieveBlog(blogId);
    return blog.map(value -> ResponseEntity.ok(blogMapper.toDto(value)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<BlogDTO>> retrieveAll() {
    return ResponseEntity.ok(blogService.retrieveAllBlogs());
  }

  @PutMapping("/{blogId}")
  public ResponseEntity<Void> update(@PathVariable("blogId") UUID blogId,
      @RequestBody BlogDTO blogDTO) {
    blogService.updateBlog(blogId, blogDTO);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{blogId}")
  public ResponseEntity<Void> delete(@PathVariable("blogId") UUID blogId) {
    blogService.deleteBlog(blogId);
    return ResponseEntity.ok().build();
  }
}
