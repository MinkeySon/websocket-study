package org.handmk.websocketstudy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.handmk.websocketstudy.data.dto.request.ChatRoomDto;
import org.handmk.websocketstudy.data.entity.ChatRoom;
import org.handmk.websocketstudy.data.repository.ChatRoomRepository;
import org.handmk.websocketstudy.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ResponseEntity<?> createChatRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomNumber(chatRoomDto.getRoomNumber())
                .build();

        chatRoomRepository.save(chatRoom);

        return ResponseEntity.ok(chatRoom);
    }
}
