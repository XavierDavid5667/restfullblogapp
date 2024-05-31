package com.blogapp.services;

import java.util.List;

import com.blogapp.dto.PostDTO;
import com.blogapp.entities.Post;

public interface PostService {
 
	PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
	
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	void deletePost(Integer postId);
	
	List<PostDTO> getAllPosts(int pageNumber, int pageSize);
	
	PostDTO getPostById(Integer postId);
	
	List<PostDTO> getAllPostByCategoryId(Integer categoryId);
	
	List<PostDTO>getAllPostByUser(Integer userId);
	
	List<Post> searchPostByKeyword(String keyword);
}