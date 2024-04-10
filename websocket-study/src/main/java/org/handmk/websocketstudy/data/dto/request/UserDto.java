package org.handmk.websocketstudy.data.dto.request;

import lombok.*;
import org.handmk.websocketstudy.data.enums.LoginType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {

    private String email;

    private String profileUrl;

    private LoginType loginType;

    private String nickName;

    private String name;

    private String password;

    private String address;

    private boolean useAble;
}
