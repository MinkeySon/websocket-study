package org.handmk.websocketstudy.data.dto.request;

import lombok.*;
import org.handmk.websocketstudy.data.enums.MessageType;
import org.handmk.websocketstudy.data.enums.UserType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private MessageType messageType; // ENTER, EXIT, SEND
    private String chatRoomId;
    private String message;
    private String senderEmail;
    private UserType userType;
}
