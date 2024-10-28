package com.sparta.chatting_test.chat.controller;

import com.sparta.chatting_test.chat.dto.ChatRoomResponseDto;
import com.sparta.chatting_test.chat.dto.InviteRequestDto;
import com.sparta.chatting_test.chat.entity.ChatMessage;
import com.sparta.chatting_test.chat.entity.ChatRoom;
import com.sparta.chatting_test.chat.service.ChatService;
import com.sparta.chatting_test.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/chatrooms")
    public ResponseEntity<?> createChatRoom(@RequestBody String name) {
        return ResponseEntity.ok().body(chatService.createOpenChatRoom(name));
    }

    // 모든 채팅방 조회
    @GetMapping("/chatrooms")
    public ResponseEntity<?> findAllChatRoom() {
        List<ChatRoomResponseDto> chatrooms = chatService.findAllRoom();
        return ResponseEntity.ok().body(chatrooms);
    }

    // 채팅방 참가
    @PostMapping("/chatrooms/{chatroomId}/join")
    public ResponseEntity<?> joinChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Long chatroomId,
                                          WebSocketSession session) {
        String email = userDetails.getUsername();
        chatService.joinChatRoom(email, chatroomId, session);
        return ResponseEntity.ok().body("이몸 등장");
    }

    // 채팅방 퇴장
    @PostMapping("/chatrooms/{chatroomId}/exit")
    public ResponseEntity<?> exitChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Long chatroomId,
                                          WebSocketSession session) {
        String email = userDetails.getUsername();
        chatService.exitChatRoom(email, chatroomId, session);
        return ResponseEntity.ok().body("이몸 퇴장");
    }

    /*-------------------------------------이 밑으로는 개인 채팅방 관련 메서드입니다.----------------------------------------*/

//    // 채팅방 생성
//    @PostMapping("/chatrooms")
//    public ResponseEntity<?> createPrivateChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                                   @RequestBody String name) {
//        String email = userDetails.getUsername();
//        return ResponseEntity.ok().body(chatService.createPrivateChatRoom(email, name));
//    }
//
//    // 채팅방 삭제
//    @PostMapping("/chatrooms/{chatroomId}/delete")
//    public ResponseEntity<?> deleteChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                            @PathVariable Long chatroomId) {
//        String email = userDetails.getUsername();
//        chatService.deleteChatRoom(email, chatroomId);
//        return ResponseEntity.ok().body("채팅방 삭제");
//    }
//
//    // 채팅방 초대
//    @PostMapping("/chatrooms/{chatroomId}/invite")
//    public ResponseEntity<?> createRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                        @PathVariable Long chatroomId,
//                                        @RequestBody InviteRequestDto inviteRequestDto) {
//        String email = userDetails.getUsername();
//        chatService.inviteUsers(email, chatroomId, inviteRequestDto);
//        return ResponseEntity.ok().body("초대 완료");
//    }

}