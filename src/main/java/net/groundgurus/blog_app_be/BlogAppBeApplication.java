package net.groundgurus.blog_app_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogAppBeApplication {

    static void main(String[] args) {
        SpringApplication.run(BlogAppBeApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runner(ApplicationContext context, BlogService blogService) {
//        return _ -> blogService.createBlogs(
//                List.of(
//                        Blog.builder()
//                                .title("Fugitive flamingo spotted in Florida")
//                                .content("""
//                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum pretium nisi justo, at viverra libero fermentum ac
//                                        """)
//                                .build(),
//                        Blog.builder()
//                                .title("To the Ends of the Earth")
//                                .content("""
//                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla tristique, quam quis mollis placerat, purus sem finibus erat, et porta diam magna efficitur diam
//                                        """)
//                                .build(),
//                        Blog.builder()
//                                .title("'Real and imminent' extinction risk")
//                                .content("""
//                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut fermentum neque quis tempor sollicitudin. Sed tristique consequat quam sed luctus
//                                        """)
//                                .build()
//                )
//        );
//    }
}
