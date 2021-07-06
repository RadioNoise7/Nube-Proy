package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.exception.ValidationExceptionsHandler;
import com.cloud.model.Comment;
import com.cloud.model.Request.CommentRequest;
import com.cloud.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentRest {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId) throws URISyntaxException{
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/comment/me")
    public ResponseEntity<List<Comment>> getCommentsByAuthUser() throws URISyntaxException{
        List<Comment> comments = commentService.getCommentsByAuthUser();
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/comment/video/{videoId}")
    public ResponseEntity<List<Comment>> getCommentsByVideo(@PathVariable Integer videoId) throws URISyntaxException{
        List<Comment> comments = commentService.getCommentsByVideo(videoId);
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping("/comment/video/{videoId}")
    public ResponseEntity<Comment> postComment(@Valid @RequestBody CommentRequest request, @PathVariable Integer videoId) throws URISyntaxException {
        Comment comment = commentService.createComment(request, videoId);
        return ResponseEntity.created(new URI("/comment/" +comment.getId())).body(comment);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Comment> putComment(@Valid @RequestBody CommentRequest request, @PathVariable Integer commentId) {
        Comment comment = commentService.updateComment(request, commentId);
        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validateExceptions(MethodArgumentNotValidException ex) {
        ValidationExceptionsHandler exHandler = new ValidationExceptionsHandler(ex);
        return exHandler.handleValidationExceptions();
    }
}
