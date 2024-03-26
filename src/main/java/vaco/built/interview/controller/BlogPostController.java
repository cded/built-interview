package vaco.built.interview.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vaco.built.interview.model.BlogPost;
import vaco.built.interview.model.request.BlogPostRequest;
import vaco.built.interview.model.request.BlogPostUpdate;
import vaco.built.interview.service.BlogPostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("")
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPostRequest blogPostReq) {
        try {
            BlogPost newPost = blogPostService.createPost(blogPostReq);
            return new ResponseEntity<>(newPost, HttpStatus.CREATED);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<BlogPost>> getAllPosts(@RequestParam(name = "categoryId", required = false) Long categoryId) {
        if(categoryId != null) {
            return new ResponseEntity<>(blogPostService.getPostsByCategoryId(categoryId), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(blogPostService.getAllPosts(), HttpStatus.OK);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable(value = "id") Long postId) {
        return blogPostService.getPostById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("")
    public ResponseEntity<BlogPost> updatePost(@RequestBody BlogPostUpdate blogPostDetails) {
        return blogPostService.updatePost(blogPostDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "id") Long postId) {
        return blogPostService.deletePost(postId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAllPosts() {
        blogPostService.deleteAllPosts();
        return ResponseEntity.ok().build();
    }
}

