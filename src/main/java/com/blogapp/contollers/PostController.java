package com.blogapp.contollers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.blogapp.config.AppConstants;
import com.blogapp.dto.PostRespose;
import com.blogapp.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import com.blogapp.dto.PostDTO;
import com.blogapp.services.PostService;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class acts as a controller for handling HTTP requests related to blog posts.
 * It maps HTTP requests to the appropriate service methods for CRUD operations on posts.
 */
@RestController
@RequestMapping("api/post")
public class PostController {

    @Value("${project.image}")
   private String path;
    // Autowiring the PostService to handle business logic related to posts.
    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;
    
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
    public ResponseEntity<PostRespose> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue =AppConstants.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy, @RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR,required = false) @PathVariable("sortDir") String sortDir) {
        return new ResponseEntity<PostRespose>(postService.getAllPosts(pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);
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
    @GetMapping("/getPostByTitle/{keyword}")
    public  ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyword) {
        return new ResponseEntity<List<PostDTO>>(postService.searchPostByKeyword(keyword), HttpStatus.OK);
    }
    @PostMapping("/uploadImage/{postId}")
    public  ResponseEntity<PostDTO> uploadImage(@PathVariable Integer postId, @RequestParam MultipartFile image) throws IOException {
        String fileName = fileService.uploadFile(path, image);
        PostDTO post = postService.getPostById(postId);
        post.setImageName(fileName);
        postService.updatePost(post, postId);
        return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
    }

    @GetMapping(value = "/downloadImage/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
