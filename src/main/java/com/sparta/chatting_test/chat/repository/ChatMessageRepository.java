package com.sparta.chatting_test.chat.repository;

import com.sparta.chatting_test.chat.dto.ChatMessage;
import com.sparta.chatting_test.chat.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatHistory, Long> {

}
