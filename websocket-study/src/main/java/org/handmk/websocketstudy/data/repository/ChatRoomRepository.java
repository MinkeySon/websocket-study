package org.handmk.websocketstudy.data.repository;

import org.handmk.websocketstudy.data.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    ChatRoom findChatRoomByRoomNumber(String number);
}
