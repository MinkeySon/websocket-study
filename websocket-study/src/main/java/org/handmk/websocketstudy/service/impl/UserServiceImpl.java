package org.handmk.websocketstudy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.handmk.websocketstudy.data.dto.request.UserDto;
import org.handmk.websocketstudy.data.entity.User;
import org.handmk.websocketstudy.data.repository.UserRepository;
import org.handmk.websocketstudy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .profileUrl(userDto.getProfileUrl())
                .loginType(userDto.getLoginType())
                .nickName(userDto.getNickName())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .useAble(userDto.isUseAble())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
