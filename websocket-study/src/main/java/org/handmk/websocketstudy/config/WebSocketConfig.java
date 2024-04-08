package org.handmk.websocketstudy.config;

import lombok.RequiredArgsConstructor;
import org.handmk.websocketstudy.config.handler.WebSocketChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketChatHandler webSocketChatHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        ws://localhost:8080/ws/chat
//        setAllowedOrigins("*") 시 모든 ip 에서 접속 가능.
        registry.addHandler(webSocketChatHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
