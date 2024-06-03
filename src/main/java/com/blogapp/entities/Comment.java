package com.blogapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
