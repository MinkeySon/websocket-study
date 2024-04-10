package org.handmk.websocketstudy.data.dto.request;

import lombok.*;
import org.handmk.websocketstudy.data.enums.LoginType;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SellerDto {
    private String email;

    private String profileUrl;

    private LoginType loginType;

    private String workShopName;

    private String name;

    private String workShopThumbnail;

    private String password;

    private String address;

    private boolean useAble;
}
