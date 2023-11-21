package com.ertedemo.domain.services;

import com.ertedemo.api.resource.messages.MessageResponse;
import com.ertedemo.domain.model.entites.Message;
import com.ertedemo.domain.model.entites.User;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> getById(Long messageId);
    Optional<Message> create(Message message);
    List<Message> getByRecipient(User recipient);


}
