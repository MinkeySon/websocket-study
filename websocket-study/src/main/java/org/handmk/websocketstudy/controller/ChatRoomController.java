package org.handmk.websocketstudy.controller;

import org.handmk.websocketstudy.data.dto.request.ChatRoomDto;
import org.handmk.websocketstudy.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat-room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createRoom(@RequestBody ChatRoomDto chatRoomDto){
        return ResponseEntity.ok(chatRoomService.createChatRoom(chatRoomDto));
    }
}
