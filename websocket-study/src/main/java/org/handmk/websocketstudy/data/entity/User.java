package org.handmk.websocketstudy.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.BUYER;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean useAble;

    @OneToMany(mappedBy = "user")
    private List<ChatRoomMembers> chatRoomMembers = new ArrayList<>();
}
