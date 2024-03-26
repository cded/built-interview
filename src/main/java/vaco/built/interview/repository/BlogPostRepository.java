package vaco.built.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vaco.built.interview.model.BlogPost;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    @Query(nativeQuery = true, value="SELECT * FROM blog_post WHERE category_id= :categoryId")
    public List<BlogPost> findAllByCategoryId(Long categoryId);
}
