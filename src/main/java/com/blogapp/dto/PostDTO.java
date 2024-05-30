package com.blogapp.dto;

import java.util.Date;
import com.blogapp.entities.Category;
import com.blogapp.entities.User;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
	private int postId;
	private String title;
	private String content;
	private String imageName;
	private Date date;
	private CategoryDTO category;
	private UserDTO user;

}
