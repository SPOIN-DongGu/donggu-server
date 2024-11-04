package com.donggu.server.domain.user.service;

import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.dto.UserJoinRequestDto;
import com.donggu.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinUser(UserJoinRequestDto dto) {

        if (!userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException();
        }

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(bCryptPasswordEncoder.encode(dto.password()))
                .role(dto.role())
                .region(dto.region())
                .height(dto.height())
                .weight(dto.weight())
                .position(dto.position())
                .build();

        userRepository.save(user);
    }
}
