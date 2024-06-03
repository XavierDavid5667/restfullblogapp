package com.blogapp.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	@Column(nullable = false)
	private String title;
	private String content;
	private String imageName;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Comment> comments=new HashSet<>();
}
