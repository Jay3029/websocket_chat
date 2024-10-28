package com.sparta.chatting_test.chat.dto;

import com.sparta.chatting_test.chat.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long id;
    private String name;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getChatRoomName();
    }
}
