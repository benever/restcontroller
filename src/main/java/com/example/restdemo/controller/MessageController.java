package com.example.restdemo.controller;

import com.example.restdemo.dto.Message;
import com.example.restdemo.dto.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class MessageController {
    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1,"For work","A new task", LocalDateTime.of(2022,12,10,17,30)),
            new Message(2,"Promo","New promo code", LocalDateTime.of(2023,2,20,12,55))
    ));

    @GetMapping("/message")
    public Iterable<Message> getMessages() {
        return messages;
    }

    @GetMapping("/message/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return messages.stream().filter(p -> p.getId() == id).findFirst();
    }

    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        int index = - 1;
        for (Message m : messages) {
            if (m.getId() == id) {
                index = messages.indexOf(m);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable int id) {
        messages.removeIf(p -> p.getId() == id);
    }
}
