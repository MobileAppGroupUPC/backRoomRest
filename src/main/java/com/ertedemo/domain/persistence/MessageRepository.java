package com.ertedemo.domain.persistence;

import com.ertedemo.domain.model.entites.Message;
import com.ertedemo.domain.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByRecipient(User recipient);
}