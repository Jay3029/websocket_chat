package com.sparta.chatting_test.chat.repository;

import com.sparta.chatting_test.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
