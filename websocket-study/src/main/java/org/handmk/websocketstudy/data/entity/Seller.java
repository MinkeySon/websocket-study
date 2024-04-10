package org.handmk.websocketstudy.data.entity;

import lombok.*;
import org.handmk.websocketstudy.data.enums.LoginType;
import org.handmk.websocketstudy.data.enums.UserType;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Seller extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType = LoginType.BASIC;

    @Column(nullable = false)
    private String workShopName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String workShopThumbnail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.SELLER;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean useAble;

    @OneToMany(mappedBy = "seller")
    private List<ChatRoomMembers> chatRoomMembers = new ArrayList<>();
}

