package org.handmk.websocketstudy.data.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ParticipateRoomMembersDto {
    private String chatRoomNumber;
    private String buyerEmail;
    private String sellerEmail;
}
