package org.handmk.websocketstudy.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.handmk.websocketstudy.data.dto.request.ChatMessageDto;
import org.handmk.websocketstudy.data.entity.ChatRoom;
import org.handmk.websocketstudy.data.entity.ChatRoomMembers;
import org.handmk.websocketstudy.data.entity.Seller;
import org.handmk.websocketstudy.data.entity.User;
import org.handmk.websocketstudy.data.enums.UserType;
import org.handmk.websocketstudy.data.repository.ChatRoomMembersRepository;
import org.handmk.websocketstudy.data.repository.ChatRoomRepository;
import org.handmk.websocketstudy.data.repository.SellerRepository;
import org.handmk.websocketstudy.data.repository.UserRepository;
import org.handmk.websocketstudy.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SellerRepository sellerRepository;
    private final ObjectMapper mapper;
    //session 관리
    private final Set<WebSocketSession> sessions = new HashSet<>();
    // chatRoomId: {session1, session2 ... }
    private final Map<String,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
    private final ChatRoomMembersRepository chatRoomMembersRepository;

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        log.info("[chat] 연결됨 \n, session id : {}", session.getId());
        sessions.add(session);
    };

    // 실제 메세지 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("[chat] get payload : {}",payload);

        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);

        ChatRoomMembers chatRoomData = getChatRoomData(chatMessageDto);
        log.info("[chat] complete search chat room data");

        if(!chatRoomSessionMap.containsKey(chatRoomData.getChatRoom().getRoomNumber())){
            log.info("[chat] add chatting room to map");
            chatRoomSessionMap.put(getChattingRoomNumber(chatRoomData),new HashSet<>());
            log.info("[chat] chat room map : {}", chatRoomSessionMap.toString());
        }

        if(isBuyer(chatMessageDto)){
            handleBuyerMessage(chatMessageDto, chatRoomData, session);
            log.info("[chat] complete send message");
        }else{
            handleSellerMessage(chatMessageDto, chatRoomData, session);
            log.info("[chat] complete send message");
        }
    };

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception{
        log.info("[chat] connection close");
        sessions.remove(session);
    }

    // Buyer Message 처리
    private void handleBuyerMessage(ChatMessageDto chatMessageDto,ChatRoomMembers chatRoomData, WebSocketSession session){
        log.info("[chat] send message");
        try{
            User buyerData = getBuyerData(chatMessageDto);
            log.info("[chat] sender user : {}", buyerData.getEmail());
            switch (chatMessageDto.getMessageType()){
                case ENTER :
                    // 해당 chatRoomId key 를 갖는 set 불러옴
                    log.info("[chat] sender message type : {}", chatMessageDto.getMessageType());
                    Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatMessageDto.getChatRoomId());
                    chatRoomSession.add(session);
                    chatMessageDto.setMessage(buyerData.getNickName() + "님이 접속하셨습니다.");
                    sendMessageToChatRoom(chatMessageDto,chatRoomSession);
                    break;
                case EXIT:
                    log.info("[chat] sender message type : {}", chatMessageDto.getMessageType());
                    chatRoomSession = chatRoomSessionMap.get(chatMessageDto.getChatRoomId());
                    if (chatRoomSession != null) {
                        chatRoomSession.remove(session); // 현재 세션 제거
                        chatRoomSession.removeIf(s -> !s.isOpen()); // 닫힌 세션 제거
                    }
                    break;
                case SEND:
                    log.info("[chat] sender message type : {}", chatMessageDto.getMessageType());
                    chatRoomSession = chatRoomSessionMap.get(chatMessageDto.getChatRoomId());
                    sendMessageToChatRoom(chatMessageDto, chatRoomSession);
            }
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
    }

    // Seller Message 처리
    private void handleSellerMessage(ChatMessageDto chatMessageDto,
                                     ChatRoomMembers chatRoomData,
                                     WebSocketSession session){
        try{
            Seller sellerData = getSellerData(chatMessageDto);
            log.info("[chat] sender user : {}", sellerData.toString());
            switch (chatMessageDto.getMessageType()){
                case ENTER :
                    log.info("[chat] sender message type : {}", chatMessageDto.getMessageType());
                    // 해당 chatRoomId key 를 갖는 set 불러옴
                    Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatMessageDto.getChatRoomId());
                    chatRoomSession.add(session);
                    chatMessageDto.setMessage(sellerData.getWorkShopName() + "님이 접속하셨습니다.");
                    sendMessageToChatRoom(chatMessageDto,chatRoomSession);
                    break;
                case EXIT:
                    log.info("[chat] sender message type : {}", chatMessageDto.getMessageType());
                    chatRoomSession = chatRoomSessionMap.get(chatMessageDto.getChatRoomId());
                    if (chatRoomSession != null) {
                        chatRoomSession.remove(session); // 현재 세션 제거
                        chatRoomSession.removeIf(s -> !s.isOpen()); // 닫힌 세션 제거
                    }
                    break;
                case SEND:
                    log.info("[chat] sender message type : {}", chatMessageDto.getMessageType());
                    chatRoomSession = chatRoomSessionMap.get(chatMessageDto.getChatRoomId());
                    sendMessageToChatRoom(chatMessageDto, chatRoomSession);
            }
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
    }

    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession){
        chatRoomSession.parallelStream().forEach(session->sendMessage(session,chatMessageDto));

    }

    /**
     * @param chatMessageDto
     * @return Buyer 여부
     */
    private boolean isBuyer(ChatMessageDto chatMessageDto){ // 보낸 메세지에서 userType 확인 메서드
        log.info("[chat] check user type");
        if(chatMessageDto.getUserType().equals(UserType.BUYER)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param chatRoomMembers
     * @return String roomNumber
     */
    private String getChattingRoomNumber(ChatRoomMembers chatRoomMembers){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByRoomNumber(chatRoomMembers.getChatRoom().getRoomNumber());
        return chatRoom.getRoomNumber();
    }
    /**
     * @param chatMessageDto
     * @return ChatRoomMembers
     */
    private ChatRoomMembers getChatRoomData(ChatMessageDto chatMessageDto){
        log.info("[chat] chatting room data 조회");
        String chatRoomId = chatMessageDto.getChatRoomId();
        log.info("[chat] chatroomid : {}", chatRoomId);
        ChatRoom findChatRoom = chatRoomRepository.findChatRoomByRoomNumber(chatRoomId);
        log.info("[chat] find room data : {}",findChatRoom.toString());

        return chatRoomMembersRepository.findChatRoomMembersByChatRoom(findChatRoom);
    }

    /**
     * @param chatMessageDto
     * @return User
     */
    private User getBuyerData(ChatMessageDto chatMessageDto){
        String buyerEmail = chatMessageDto.getSenderEmail();
        return userRepository.findUserByEmail(buyerEmail);
    }

    /**
     * @param chatMessageDto
     * @return Seller
     */
    private Seller getSellerData(ChatMessageDto chatMessageDto){
        String sellerEmail = chatMessageDto.getSenderEmail();
        return sellerRepository.findSellerByEmail(sellerEmail);
    }


    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
