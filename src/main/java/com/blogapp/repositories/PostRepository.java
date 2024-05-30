package com.blogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;

public interface PostRepository extends JpaRepository<Post,Integer> {
	
	//custom finder methods
	//https://www.baeldung.com/spring-data-derived-queries
	List<Post>findByUser(User user);
	
	List<Post>findByCategory(Category category);

}
