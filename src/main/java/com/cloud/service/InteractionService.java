package com.cloud.service;

import java.util.Optional;

import com.cloud.exception.ConflictException;
import com.cloud.exception.NotFoundException;
import com.cloud.model.Interaction;
import com.cloud.repository.InteractionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionService {

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;
    
    public Interaction getInteractionsByUserAndVideo(Integer userId, Integer videoId){
        Optional<Interaction> interaction = interactionRepository.findByUserIdAndVideoId(userId, videoId);
        if(!interaction.isPresent())
            throw new NotFoundException("No existe interacción entre el usuario y el video");

        return interaction.get();
    }

    public Interaction createInteraction(Integer userId, Integer videoId, Boolean status){
        if(interactionRepository.findByUserIdAndVideoId(userId, videoId).isPresent())
            throw new ConflictException("El valor para esta combinación ya existe");
        Interaction interaction = new Interaction();
        interaction.setUser(userService.getUserById(userId));
        interaction.setVideo(videoService.getVideoById(videoId));
        interaction.setLikeStatus(status);
        interaction = interactionRepository.save(interaction);
        return interaction;
    }
}
