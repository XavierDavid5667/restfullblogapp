package com.blogapp.services;

import java.util.List;

import com.blogapp.dto.PostDTO;
import com.blogapp.dto.PostRespose;
import com.blogapp.entities.Post;

public interface PostService {
 
	PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
	
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	void deletePost(Integer postId);
	
	PostRespose getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir);
	
	PostDTO getPostById(Integer postId);
	
	List<PostDTO> getAllPostByCategoryId(Integer categoryId);
	
	List<PostDTO>getAllPostByUser(Integer userId);
	
	List<Post> searchPostByKeyword(String keyword);
}
