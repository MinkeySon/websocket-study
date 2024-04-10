package org.handmk.websocketstudy.data.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BaseResponseDto {
    private String msg;
    private boolean status;
}
