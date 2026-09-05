package net.groundgurus.blog_app_be.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.groundgurus.blog_app_be.dto.BlogDTO;
import net.groundgurus.blog_app_be.mapper.BlogMapper;
import net.groundgurus.blog_app_be.model.Blog;
import net.groundgurus.blog_app_be.repository.BlogRepository;
import net.groundgurus.blog_app_be.util.BlogUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

  public static final int MAX_BLOGS = 10;

  private final BlogRepository blogRepository;
  private final BlogMapper blogMapper;

  public void createBlog(BlogDTO blogDTO) {
    blogDTO.setBlogId(UUID.randomUUID());
    blogDTO.setSubUrl(BlogUtils.createBlogUrl(blogDTO.getTitle()));
    blogRepository.save(blogMapper.toEntity(blogDTO));
  }

//    public void createBlogs(List<Blog> blogs) {
//        blogs.forEach(blog -> blog.setSubUrl(BlogUtils.generateSubUrl(blog.getTitle())));
//        blogRepository.saveAll(blogs);
//    }

  public Optional<Blog> retrieveBlog(UUID blogId) {
    var blog = blogRepository.findByBlogId(blogId);
    if (Objects.nonNull(blog)) {
      return Optional.of(blog);
    }
    return Optional.empty();
  }

  public List<BlogDTO> retrieveAllBlogs() {
    return blogRepository.findAll(MAX_BLOGS)
        .stream()
        .map(blogMapper::toDto)
        .toList();
  }

  public void updateBlog(UUID blogId, BlogDTO blogDTO) {
    var blog = blogRepository.findByBlogId(blogId);
    if (Objects.nonNull(blog)) {
      blog.setTitle(blogDTO.getTitle());
      blog.setContent(blogDTO.getContent());
      blogRepository.save(blog);
    }
  }

  public void deleteBlog(UUID blogId) {
    var blog = blogRepository.findByBlogId(blogId);
    if (Objects.nonNull(blog)) {
      blogRepository.delete(blog);
    }
  }
}
