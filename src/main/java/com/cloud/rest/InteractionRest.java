package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.validation.Valid;

import com.cloud.exception.ValidationExceptionsHandler;
import com.cloud.model.Interaction;
import com.cloud.model.request.InteractionRequest;
import com.cloud.service.AuthService;
import com.cloud.service.InteractionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InteractionRest {

    @Autowired
    private AuthService authService;

    @Autowired
    private InteractionService interactionService;

    @GetMapping("/interactions/user/{userId}/video/{videoId}")
    public ResponseEntity<Interaction> getInteractionByUserAndVideo(@PathVariable Integer userId,
            @PathVariable Integer videoId) throws URISyntaxException {

        Interaction interaction = interactionService.getInteractionsByUserAndVideo(userId, videoId);
        return ResponseEntity.ok().body(interaction);
    }

    @PostMapping("/interactions/me/video/{videoId}")
    public ResponseEntity<Interaction> postInteraction(@Valid @RequestBody InteractionRequest request,
            @PathVariable Integer videoId) throws URISyntaxException {

        Integer userId = authService.getAuthUser().getId();
        Interaction interaction = interactionService.createInteraction(userId, videoId, request.getStatus());
        return ResponseEntity.created(new URI("/interactions/user/" + userId + "/video/" + videoId)).body(interaction);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validateExceptions(MethodArgumentNotValidException ex) {
        ValidationExceptionsHandler exHandler = new ValidationExceptionsHandler(ex);
        return exHandler.handleValidationExceptions();
    }
}
