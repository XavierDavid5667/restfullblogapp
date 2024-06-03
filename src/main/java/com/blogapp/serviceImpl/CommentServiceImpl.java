package com.blogapp.serviceImpl;

import com.blogapp.dto.CommentDTO;
import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exception.ResouceNotFoundException;
import com.blogapp.repositories.CommentRepository;
import com.blogapp.repositories.PostRepository;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResouceNotFoundException("Post with id" + postId + "not found!"));
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        commentRepository.save(comment);
        commentDTO.setId(comment.getId());
        return commentDTO;
    }

    @Override
    public void deleteComment(Integer commentId) {
     commentRepository.findById(commentId).orElseThrow(() -> new ResouceNotFoundException("Comment with id" + commentId + "not found!"));
     commentRepository.deleteById(commentId);
    }
}
