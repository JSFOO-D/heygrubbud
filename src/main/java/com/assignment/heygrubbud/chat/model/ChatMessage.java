package com.assignment.heygrubbud.chat.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
}
