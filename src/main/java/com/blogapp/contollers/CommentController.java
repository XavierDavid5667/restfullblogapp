package com.blogapp.contollers;


import com.blogapp.dto.CommentDTO;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment/{postId}")
    public ResponseEntity<CommentDTO>createComment(@RequestBody CommentDTO commentDTO,@PathVariable Integer postId){
        return ResponseEntity.ok(commentService.createComment(commentDTO, postId));
    }
    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
