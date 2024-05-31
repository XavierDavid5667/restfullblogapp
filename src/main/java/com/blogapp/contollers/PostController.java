package com.blogapp.contollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blogapp.dto.PostDTO;
import com.blogapp.services.PostService;

/**
 * This class acts as a controller for handling HTTP requests related to blog posts.
 * It maps HTTP requests to the appropriate service methods for CRUD operations on posts.
 */
@RestController
@RequestMapping("api/post")
public class PostController {
    
    // Autowiring the PostService to handle business logic related to posts.
    @Autowired
    PostService postService;
    
    /**
     * Creates a new post.
     * @param postDTO The data transfer object containing post details.
     * @param userId The ID of the user creating the post.
     * @param categoryId The ID of the category to which the post belongs.
     * @return ResponseEntity containing the created PostDTO and HTTP status.
     */
    @PostMapping("/user/{userId}/category/{categoryId}/createPost")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        return new ResponseEntity<PostDTO>(postService.createPost(postDTO, userId, categoryId), HttpStatus.OK);
    }
    
    /**
     * Retrieves all posts created by a specific user.
     * @param userId The ID of the user whose posts are to be retrieved.
     * @return ResponseEntity containing a list of PostDTO and HTTP status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
        return new ResponseEntity<List<PostDTO>>(postService.getAllPostByUser(userId), HttpStatus.OK);
    }
    
    /**
     * Retrieves all posts in a specific category.
     * @param categoryId The ID of the category whose posts are to be retrieved.
     * @return ResponseEntity containing a list of PostDTO and HTTP status.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        return new ResponseEntity<List<PostDTO>>(postService.getAllPostByCategoryId(categoryId), HttpStatus.OK);
    }
    
    /**
     * Updates an existing post.
     * @param postDTO The data transfer object containing updated post details.
     * @param postId The ID of the post to be updated.
     * @return ResponseEntity containing the updated PostDTO and HTTP status.
     */
    @PutMapping("/updatepost/{postId}")    
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {
       return new ResponseEntity<PostDTO>(postService.updatePost(postDTO, postId), HttpStatus.OK);
    }
    
    /**
     * Deletes a post.
     * @param postId The ID of the post to be deleted.
     * @return ResponseEntity containing a success message and HTTP status.
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<String>("Post deleted successfully!", HttpStatus.OK);
    }
    
    /**
     * Retrieves all posts.
     * @return ResponseEntity containing a list of all PostDTO and HTTP status.
     */
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostDTO>> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        return new ResponseEntity<List<PostDTO>>(postService.getAllPosts(pageNumber, pageSize), HttpStatus.OK);
    }
    
    /**
     * Retrieves a post by its ID.
     * @param postId The ID of the post to be retrieved.
     * @return ResponseEntity containing the PostDTO and HTTP status.
     */
    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        return new ResponseEntity<PostDTO>(postService.getPostById(postId), HttpStatus.OK);
    }
}
