package com.ertedemo.api.resource.messages;

import lombok.Data;

@Data
public class MessageRequest {
    private Long authorId;

    private Long recipientId;

    private String content;
}
