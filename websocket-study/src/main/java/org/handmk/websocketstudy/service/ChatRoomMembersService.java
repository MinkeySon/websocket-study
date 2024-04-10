package org.handmk.websocketstudy.service;

import org.handmk.websocketstudy.data.dto.request.ParticipateRoomMembersDto;
import org.springframework.http.ResponseEntity;

public interface ChatRoomMembersService {
    ResponseEntity<?> participateChatRoom(ParticipateRoomMembersDto participateRoomMembersDto);
}
