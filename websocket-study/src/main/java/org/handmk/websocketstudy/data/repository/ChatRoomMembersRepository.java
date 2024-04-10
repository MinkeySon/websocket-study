package org.handmk.websocketstudy.data.repository;

import org.handmk.websocketstudy.data.entity.ChatRoom;
import org.handmk.websocketstudy.data.entity.ChatRoomMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMembersRepository extends JpaRepository<ChatRoomMembers, Long> {
    ChatRoomMembers findChatRoomMembersByChatRoom(ChatRoom chatRoom);
}
