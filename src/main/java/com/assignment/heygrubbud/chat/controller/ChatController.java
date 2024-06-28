package com.assignment.heygrubbud.chat.controller;

import com.assignment.heygrubbud.chat.model.ChatMessage;
import com.assignment.heygrubbud.chat.model.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
public class ChatController {

    List<String> locationList = new ArrayList<String>();

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        log.info("chatMessage: " + chatMessage);
        boolean isReset = false;
        if(MessageType.LOCATION.equals(chatMessage.getType())){
            String locationToAdd = chatMessage.getContent().split(":")[1];
            log.info("Adding location to list : " + locationToAdd);
            locationList.add(locationToAdd);
            log.info("new list of locations : " + locationList);
        }
        // generate random location and return location as chat message.
        if(MessageType.GENERATE.equals(chatMessage.getType())){
            String randomiseLocation = "";
            log.info("locationList size : " + locationList.size());
            if(locationList.size() > 1){
                // Randomise location
                Random r = new Random();
                randomiseLocation = locationList.get(r.nextInt(locationList.size()));
                isReset = true;
            } else if(locationList.size() == 0) {
                // Error handling if list is empty to prevent index out of bounds
                chatMessage.setSender("ADMIN");
                chatMessage.setType(MessageType.GENERATED);
                chatMessage.setContent("Error : The location list is empty.");
                isReset = false;
            } else {
                // only 1 location in list
                randomiseLocation = locationList.get(0);
                isReset = true;
            }
            if(isReset) {
                chatMessage.setSender("ADMIN");
                chatMessage.setType(MessageType.GENERATED);
                chatMessage.setContent("The location list will be reset. Location has been chosen : " + randomiseLocation);
                log.info("Resetting location list after generating.");
                locationList.clear();
            }
        }

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
