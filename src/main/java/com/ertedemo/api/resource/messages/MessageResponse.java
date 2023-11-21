package com.ertedemo.api.resource.messages;

import com.ertedemo.domain.model.entites.Message;
import lombok.Data;

@Data
public class MessageResponse {

    private String content;
    private String authorName;
    private Long authorId;

    public MessageResponse(Message message){
        this.content = message.getContent();
        this.authorName = message.getAuthor().getName() +" "+message.getAuthor().getLastName();
        this.authorId = message.getAuthor().getId();
    }
}