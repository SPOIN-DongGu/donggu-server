package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.dto.UserJoinRequestDto;
import com.donggu.server.domain.user.repository.UserRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinUser(UserJoinRequestDto dto) {

        if (userRepository.existsByUsername(dto.username())) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
        }

        User user = User.builder()
                .username(dto.username())
                .password(bCryptPasswordEncoder.encode(dto.password()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    // 테스트를 위한 임시 메서드
    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
