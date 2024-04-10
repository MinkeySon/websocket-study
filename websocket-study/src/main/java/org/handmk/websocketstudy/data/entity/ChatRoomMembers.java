package org.handmk.websocketstudy.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table
public class ChatRoomMembers extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "BUYER_ID")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    @JsonBackReference
    private Seller seller;
}
