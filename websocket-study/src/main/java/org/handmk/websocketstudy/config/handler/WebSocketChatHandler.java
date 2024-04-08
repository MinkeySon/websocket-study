package org.handmk.websocketstudy.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.handmk.websocketstudy.data.dto.ChatMessageDto;
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
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    //session 관리
    private final Set<WebSocketSession> sessions = new HashSet<>();
    // chatRoomId: {session1, session2 ... }
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        log.info("[chat] 연결됨 \n, session id : {}", session.getId());
        try{
            sessions.add(session);
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
    };

    // 실제 메세지 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("[chat] get payload : {}",payload);

        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
        log.info("session {}", chatMessageDto.toString());

        Long chatRoomId = chatMessageDto.getChatRoomId();

        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            log.info("[chat] create new chatting room");
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        if(chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)){
            chatRoomSession.add(session);
        }
        if (chatRoomSession.size()>=3){
            removeClosedSession(chatRoomSession);
        }
        sendMessageToChatRoom(chatMessageDto,chatRoomSession);

    };
    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception{
        log.info("[chat] connection close");
        sessions.remove(session);
    }

    private void removeClosedSession(Set<WebSocketSession> chatRoomSession){
        chatRoomSession.removeIf(session->!sessions.contains(session));
    }
    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession> chatRoomSession){
        chatRoomSession.parallelStream().forEach(session->sendMessage(session,chatMessageDto));

    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
