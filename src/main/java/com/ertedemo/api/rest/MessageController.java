package com.ertedemo.api.rest;

import com.ertedemo.api.resource.messages.MessageRequest;
import com.ertedemo.api.resource.messages.MessageResponse;
import com.ertedemo.domain.model.entites.Message;
import com.ertedemo.domain.model.entites.User;
import com.ertedemo.domain.services.MessageService;
import com.ertedemo.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;


    @GetMapping("{messageId}")
    public ResponseEntity<MessageResponse> getMessageById(@PathVariable Long messageId) {

        Optional<Message> message = messageService.getById(messageId);

       if (message.isEmpty())
           return ResponseEntity.badRequest().build();

       return  ResponseEntity.ok(new MessageResponse(message.get()));
    }

    @PostMapping
    public ResponseEntity<String> addMessage(@RequestBody MessageRequest messageRequest) {

        Optional<User> author = userService.getById(messageRequest.getAuthorId());
        Optional<User> recipient = userService.getById(messageRequest.getRecipientId());

        if (author.isPresent() && recipient.isPresent()) {

            Message message = new Message(author.get(), recipient.get(), messageRequest);
            messageService.create(message);

            return ResponseEntity.ok("Message added successfully.");
        }
        return ResponseEntity.badRequest().body("error to added message");
    }


    //Devuelve lista de mensajes dado IdRecipient
    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<MessageResponse>> getMessageByRecipientId(@PathVariable Long recipientId) {

        Optional<User> recipient = userService.getById(recipientId);

        if (recipient.isPresent()) {
            List<MessageResponse> messages = messageService.getByRecipient(recipient.get()).stream()
                    .map(message -> new MessageResponse(message))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(messages);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
