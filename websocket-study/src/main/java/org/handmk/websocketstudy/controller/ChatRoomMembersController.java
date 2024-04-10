package org.handmk.websocketstudy.controller;

import org.handmk.websocketstudy.data.dto.request.ParticipateRoomMembersDto;
import org.handmk.websocketstudy.service.ChatRoomMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chatroom-member")
public class ChatRoomMembersController {
    private final ChatRoomMembersService chatRoomMembersService;

    @Autowired
    public ChatRoomMembersController(ChatRoomMembersService chatRoomMembersService) {
        this.chatRoomMembersService = chatRoomMembersService;
    }

    @PostMapping
    public ResponseEntity<?> participateMembers(@RequestBody ParticipateRoomMembersDto participateRoomMembersDto){
        return ResponseEntity.ok(chatRoomMembersService.participateChatRoom(participateRoomMembersDto));
    }
}
