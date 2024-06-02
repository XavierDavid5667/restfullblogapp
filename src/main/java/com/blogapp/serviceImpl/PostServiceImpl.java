package com.blogapp.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.blogapp.dto.PostRespose;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.blogapp.dto.PostDTO;
import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exception.ResouceNotFoundException;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.repositories.PostRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.PostService;
import lombok.extern.slf4j.Slf4j;

/**
 * This class implements the PostService interface to provide the business
 * logic for CRUD operations on posts.
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    // Autowiring the PostRepository to interact with the database.
    @Autowired
    PostRepository postRepository;

    // Autowiring the ModelMapper to convert between Post and PostDTO.
    @Autowired
    ModelMapper mapper;

    // Autowiring the UserRepository to interact with user-related database operations.
    @Autowired
    UserRepository userRepository;

    // Autowiring the CategoryRepository to interact with category-related database operations.
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Creates a new post.
     * 
     * @param postDTO The data transfer object containing post details.
     * @param userId The ID of the user creating the post.
     * @param categoryId The ID of the category to which the post belongs.
     * @return The created PostDTO.
     */
    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        log.info("Creating new post for user ID: {} and category ID: {}", userId, categoryId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResouceNotFoundException("User with given id " + userId + " not found!"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResouceNotFoundException("Category with given id " + categoryId + " not found!"));
        Post post = mapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with ID: {}", savedPost.getPostId());
        return mapper.map(savedPost, PostDTO.class);
    }

    /**
     * Updates an existing post.
     * 
     * @param postDTO The data transfer object containing updated post details.
     * @param postId The ID of the post to be updated.
     * @return The updated PostDTO.
     * @throws ResouceNotFoundException if the post is not found.
     */
    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        log.info("Updating post with ID: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResouceNotFoundException("Post with given id " + postId + " not found!"));
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setImageName(postDTO.getImageName());
        Post savedPost = postRepository.save(post);
        log.info("Post with ID: {} updated successfully", postId);
        return mapper.map(savedPost, PostDTO.class);
    }

    /**
     * Deletes a post by its ID.
     * 
     * @param postId The ID of the post to be deleted.
     * @throws ResouceNotFoundException if the post is not found.
     */
    @Override
    public void deletePost(Integer postId) {
        log.info("Deleting post with ID: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->  new ResouceNotFoundException("Post with given id " + postId + " not found!"));
        postRepository.delete(post);
        log.info("Post with ID: {} deleted successfully", postId);
    }

    /**
     * Retrieves all posts.
     * 
     * @return A list of all PostDTO.
     */
    @Override
    public PostRespose getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir) {
        log.info("Retrieving all posts");
        //Pagination is often helpful when we have a large dataset and we want to present it to the user in smaller chunks.
        Sort sort=sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable =  PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.getContent();
        log.info("{} Posts retrieved successfully", posts.size());
        List<PostDTO> listOFPostDto = posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostRespose response = new PostRespose();
        response.setPosts(listOFPostDto);
        response.setPageNumber(postPage.getNumber());
        response.setPageSize(postPage.getSize());
        response.setTotalElements(postPage.getTotalElements());
        response.setTotalPages(postPage.getTotalPages());
        response.setLastPage(postPage.isLast());
        return response;
    }

    /**
     * Retrieves a post by its ID.
     * 
     * @param postId The ID of the post to be retrieved.
     * @return The PostDTO.
     * @throws ResouceNotFoundException if the post is not found.
     */
    @Override
    public PostDTO getPostById(Integer postId) {
        log.info("Retrieving post with ID: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResouceNotFoundException("Post with given ID " + postId + " not found!"));
        log.info("Post with ID: {} retrieved successfully", postId);
        return mapper.map(post, PostDTO.class);
    }

    /**
     * Retrieves all posts by category ID.
     * 
     * @param categoryId The ID of the category.
     * @return A list of PostDTO belonging to the specified category.
     * @throws ResouceNotFoundException if the category is not found.
     */
    @Override
    public List<PostDTO> getAllPostByCategoryId(Integer categoryId) {
        log.info("Retrieving all posts for category ID: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResouceNotFoundException("Category with given id " + categoryId + " not found!"));
        List<Post> posts = postRepository.findByCategory(category);
        log.info("All posts for category ID: {} retrieved successfully", categoryId);
        return posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    /**
     * Retrieves all posts by user ID.
     * 
     * @param userId The ID of the user.
     * @return A list of PostDTO belonging to the specified user.
     * @throws ResouceNotFoundException if the user is not found.
     */
    @Override
    public List<PostDTO> getAllPostByUser(Integer userId) {
        log.info("Retrieving all posts for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResouceNotFoundException("User with given id " + userId + " not found!"));
        List<Post> posts = postRepository.findByUser(user);
        log.info("All posts for user ID: {} retrieved successfully", userId);
        return posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    /**
     * Searches for posts by keyword.
     * 
     * @param keyword The keyword to search for.
     * @return A list of posts that contain the specified keyword.
     */
    @Override
    public List<PostDTO> searchPostByKeyword(String keyword) {
        log.info("Searching for posts with keyword: {}", keyword);
        List<Post> byTitleContaining = postRepository.findByTitleContaining(keyword);
        return byTitleContaining.stream().map(post ->this.mapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }
}
