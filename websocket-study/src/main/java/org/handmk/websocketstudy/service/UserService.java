package org.handmk.websocketstudy.service;

import org.handmk.websocketstudy.data.dto.request.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> createUser(UserDto userDto);
}
