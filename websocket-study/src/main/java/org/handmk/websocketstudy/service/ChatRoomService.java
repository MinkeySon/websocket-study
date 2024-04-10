package org.handmk.websocketstudy.service;

import org.handmk.websocketstudy.data.dto.request.ChatRoomDto;
import org.springframework.http.ResponseEntity;

public interface ChatRoomService {
    ResponseEntity<?> createChatRoom(ChatRoomDto chatRoomDto);
}
