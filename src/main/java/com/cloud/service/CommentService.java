package com.cloud.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.exception.NotFoundException;
import com.cloud.model.Comment;
import com.cloud.model.Request.CommentRequest;
import com.cloud.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private VideoService videoService;

    /**
    * Este es el metodo que devuelve los comentarios
    * @return comentarios
    *
    */
    public List<Comment> getAllComments() {
        List<Comment> comments = new LinkedList<>();
        commentRepository.findAll().iterator().forEachRemaining(comments::add);
        return comments;
    }

    /**
    * Este es el metodo que devuelve el equpo por cuenta
    * @param id id del comentario 
    * @return el comentario con el id ingresado
    * @throws NotFoundException si el comentario seleccionado no existe
    */
    public Comment getCommentById(Integer id) throws NotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent())
            throw new NotFoundException("No se encontró el comentario con id "+id);
        return comment.get();
    }

    public Comment createComment(CommentRequest request, Integer videoId){
        Comment comment = new Comment();
        comment.setUsuario(authService.getAuthUser());
        comment.setVideo(videoService.getVideoById(videoId));
        comment.setContent(request.getContent());
        comment = commentRepository.save(comment);
        return comment;
    }

    public Comment updateComment(CommentRequest request, Integer commentId){
        Comment comment = getCommentById(commentId);
        comment.setContent(request.getContent());
        comment = commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Integer commentId){
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByAuthUser(){
        Optional<List<Comment>> comments = commentRepository.findByUserId(authService.getAuthUser().getId());
        return comments.get();
    }

    public List<Comment> getCommentsByVideo(Integer videoId){
        Optional<List<Comment>> comments = commentRepository.findByVideoId(videoService.getVideoById(videoId).getId());
        return comments.get();
    }
    
}
