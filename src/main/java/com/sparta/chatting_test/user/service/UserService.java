package com.sparta.chatting_test.user.service;

import com.sparta.chatting_test.common.security.JwtUtil;
import com.sparta.chatting_test.common.security.PasswordEncoder;
import com.sparta.chatting_test.user.dto.UserRequestDto;
import com.sparta.chatting_test.user.dto.UserResponseDto;
import com.sparta.chatting_test.user.entity.User;
import com.sparta.chatting_test.user.entity.UserRole;
import com.sparta.chatting_test.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Transactional
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        UserRole role = Optional.ofNullable(userRequestDto.getUserRole())
                .orElse(UserRole.USER);
        UserRole.of(String.valueOf(role));

        User user = new User(
                userRequestDto.getEmail(),
                password,
                userRequestDto.getNickname(),
                role
        );
        userRepository.save(user);
        return UserResponseDto.from(user);

    }

    @Transactional
    public String login(UserRequestDto userRequestDto) {
        // 사용자 이메일로 사용자 찾기
        User user = userRepository.findByEmailOrElseThrow(userRequestDto.getEmail());

        // 비밀번호 확인
        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.createToken(user.getEmail(),user.getUserRole());
        return token;
    }

    @Transactional
    public void deleteUser(String email, String password) {
        User user = userRepository.findByEmailOrElseThrow(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치 하지 않습니다.");
        }
        user.deleteAccount();
        userRepository.save(user);

    }
}
