package org.handmk.websocketstudy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.handmk.websocketstudy.data.dto.request.ParticipateRoomMembersDto;
import org.handmk.websocketstudy.data.entity.ChatRoom;
import org.handmk.websocketstudy.data.entity.ChatRoomMembers;
import org.handmk.websocketstudy.data.entity.Seller;
import org.handmk.websocketstudy.data.entity.User;
import org.handmk.websocketstudy.data.repository.ChatRoomMembersRepository;
import org.handmk.websocketstudy.data.repository.ChatRoomRepository;
import org.handmk.websocketstudy.data.repository.SellerRepository;
import org.handmk.websocketstudy.data.repository.UserRepository;
import org.handmk.websocketstudy.service.ChatRoomMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatRoomMembersServiceImpl implements ChatRoomMembersService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ChatRoomMembersRepository chatRoomMembersRepository;


    @Autowired
    public ChatRoomMembersServiceImpl(ChatRoomRepository chatRoomRepository, UserRepository userRepository, SellerRepository sellerRepository, ChatRoomMembersRepository chatRoomMembersRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.chatRoomMembersRepository = chatRoomMembersRepository;
    }


    @Override
    public ResponseEntity<?> participateChatRoom(ParticipateRoomMembersDto participateRoomMembersDto) {
        User buyer = userRepository.findUserByEmail(participateRoomMembersDto.getBuyerEmail());
        Seller seller = sellerRepository.findSellerByEmail(participateRoomMembersDto.getSellerEmail());

        ChatRoom chatRoom = ChatRoom.builder()
                .roomNumber(participateRoomMembersDto.getChatRoomNumber())
                .build();
        chatRoomRepository.save(chatRoom);

        log.info("[chatroom] chatroom : {}",chatRoom.getRoomNumber());

        ChatRoomMembers chatRoomMembers = ChatRoomMembers.builder()
                .chatRoom(chatRoom)
                .user(buyer)
                .seller(seller)
                .build();
        chatRoomMembersRepository.save(chatRoomMembers);
        return ResponseEntity.ok(chatRoomMembers);
    }
}
