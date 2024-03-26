package vaco.built.interview.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vaco.built.interview.model.BlogPost;
import vaco.built.interview.model.Category;
import vaco.built.interview.model.request.BlogPostRequest;
import vaco.built.interview.repository.BlogPostRepository;
import vaco.built.interview.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogPostServiceTest {
    @Mock
    private BlogPostRepository blogPostRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private BlogPostService blogPostService;

    @Test
    void getPostById() {
        Long postId = 1234L;
        BlogPost post = new BlogPost();
        post.setId(postId);

        given(blogPostRepository.findById(postId)).willReturn(Optional.of(post));
        Optional<BlogPost> result = blogPostService.getPostById(postId);
        verify(blogPostRepository).findById(postId);

        assertEquals(result.get(), post);
    }

    @Test
    void shouldSavePost() {
        BlogPostRequest postReq = new BlogPostRequest();
        postReq.setCategoryId(1L);
        postReq.setTitle("test title");
        postReq.setText("New Blog post");


        Category category = new Category();
        category.setId(1L);
        category.setName("Test");

        BlogPost post = new BlogPost();
        post.setId(12L);
        post.setTitle("test title");
        post.setContents("New Blog post");
        post.setCategory(category);
        post.setTimestamp(LocalDateTime.now());

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        when(blogPostRepository.save(Mockito.any(BlogPost.class))).thenReturn(post);
        BlogPost newPost = blogPostService.createPost(postReq);

        verify(blogPostRepository, times(1)).save(any(BlogPost.class));

        assertEquals(newPost, post);
    }
}
