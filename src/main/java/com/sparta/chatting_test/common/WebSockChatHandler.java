package com.sparta.chatting_test.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.chatting_test.chat.dto.ChatMessage;
import com.sparta.chatting_test.chat.entity.ChatHistory;
import com.sparta.chatting_test.chat.entity.ChatRoom;
import com.sparta.chatting_test.chat.repository.ChatMessageRepository;
import com.sparta.chatting_test.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoom(chatMessage.getRoomId());

        if(room != null) {
            room.handleActions(session, chatMessage, chatService);
            ChatHistory chatHistory = new ChatHistory(chatMessage.getMessageType(), chatMessage.getSender(), chatMessage.getMessage(), room);
            chatMessageRepository.save(chatHistory);
            log.info("chat is successful!");
        } else {
            log.warn("Chat room not found for roomId: {}", chatMessage.getRoomId());
        }

    }
}