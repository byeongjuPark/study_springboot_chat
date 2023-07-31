package com.example.study_springboot_chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.study_springboot_chat.dto.ChatMessage;
import com.example.study_springboot_chat.dto.ChatRoom;
import com.example.study_springboot_chat.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler{
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){
        String payload = message.getPayload();
        ChatMessage chatMessage;
        try {
            chatMessage = objectMapper.readValue(payload, ChatMessage.class);
            ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
            chatRoom.handlerActions(session, chatMessage, chatService);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
