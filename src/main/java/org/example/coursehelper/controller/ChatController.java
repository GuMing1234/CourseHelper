package org.example.coursehelper.controller;

import org.example.coursehelper.service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @RequestMapping("/chat")
    public String chat(@RequestParam String msg){
        return chatService.chat(msg);
    }
}
