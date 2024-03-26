package vaco.built.interview.service;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vaco.built.interview.model.BlogPost;
import vaco.built.interview.model.Category;
import vaco.built.interview.model.request.BlogPostRequest;
import vaco.built.interview.model.request.BlogPostUpdate;
import vaco.built.interview.repository.BlogPostRepository;
import vaco.built.interview.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    public BlogPostService(BlogPostRepository blogPostRepository, CategoryRepository categoryRepository) {
        this.blogPostRepository = blogPostRepository;
        this.categoryRepository = categoryRepository;
    }

    public BlogPost createPost(BlogPostRequest blogPostReq) {
        logger.info("Creating New Blog Post...");
        BlogPost post = new BlogPost();

        post.setTitle(blogPostReq.getTitle());
        post.setContents(blogPostReq.getText());

        Optional<Category> category = categoryRepository.findById(blogPostReq.getCategoryId());
        if(category.isEmpty()) {
            throw new ValidationException("Category cannot be null.");
        }
        post.setCategory(category.get());

        post.setTimestamp(LocalDateTime.now());

        return blogPostRepository.save(post);
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll(Sort.by(Sort.Direction.ASC, "timestamp"));
    }

    public Optional<BlogPost> getPostById(Long postId) {
        return blogPostRepository.findById(postId);
    }

    public List<BlogPost> getPostsByCategoryId(Long categoryId) {
        return blogPostRepository.findAllByCategoryId(categoryId);
    }

    public Optional<BlogPost> updatePost(BlogPostUpdate blogPostDetails) {
        Long postId = blogPostDetails.getId();
        return blogPostRepository.findById(postId)
                .map(blogPost -> {
                    Optional<Category> category = categoryRepository.findById(blogPostDetails.getCategoryId());
                    blogPost.setTitle(blogPostDetails.getTitle());
                    blogPost.setContents(blogPostDetails.getText());
                    blogPost.setTimestamp(blogPostDetails.getTimestamp());
                    blogPost.setCategory(category.get());
                    return blogPostRepository.save(blogPost);
                });
    }

    public boolean deletePost(Long postId) {
        return blogPostRepository.findById(postId)
                .map(blogPost -> {
                    blogPostRepository.delete(blogPost);
                    return true;
                }).orElse(false);
    }

    public void deleteAllPosts() {
        blogPostRepository.deleteAll();
    }
}
