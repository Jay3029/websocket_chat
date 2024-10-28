package com.sparta.chatting_test.user;

import com.sparta.chatting_test.common.security.CustomUserDetails;
import com.sparta.chatting_test.common.security.JwtUtil;
import com.sparta.chatting_test.user.dto.UserRequestDto;
import com.sparta.chatting_test.user.dto.UserResponseDto;
import com.sparta.chatting_test.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto user = userService.signUp(userRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto userRequestDto) {
        String token = userService.login(userRequestDto);
        return ResponseEntity.ok().body(token);
    }
    @PatchMapping("/delete")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails authUser, @RequestBody UserRequestDto userRequestDto) {
        log.info("user:{}",authUser);
        String email = authUser.getEmail();
        userService.deleteUser(email,userRequestDto.getPassword());
        return ResponseEntity.ok("회원탈퇴 성공");

    }
}
