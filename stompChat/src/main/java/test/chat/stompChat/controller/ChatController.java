package test.chat.stompChat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import test.chat.stompChat.model.ChatMessage;
import test.chat.stompChat.service.ChatMessageService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    // 🌐 STOMP 엔드포인트: /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    public void send(ChatMessage message) {
        log.info("📩 [STOMP] message: {}", message);
        chatMessageService.handleIncomingMessage(message);
        // 반환값은 없고, 브로드캐스트는 서비스에서 처리
    }

    // 📜 특정 방 채팅 내역 조회 (REST API)
    @GetMapping("/api/chat/rooms/{roomId}/messages")
    @ResponseBody
    public List<ChatMessage> getRoomMessages(@PathVariable Long roomId) {
        return chatMessageService.getHistory(roomId);
    }

    // ✅ 특정 방(roomId)의 메시지 전체 삭제
    @DeleteMapping("/api/chat/rooms/{roomId}/messages")
    @ResponseBody
    public ResponseEntity<Void> deleteRoomMessages(@PathVariable Long roomId) {
        chatMessageService.deleteAllInRoom(roomId);
        return ResponseEntity.noContent().build(); // 204
    }

    // ✅ 전체 메시지 싹 날리는 용도(테스트용)
    @DeleteMapping("/api/chat/messages")
    @ResponseBody
    public ResponseEntity<Void> deleteAllMessages() {
        chatMessageService.deleteAllMessages();
        return ResponseEntity.noContent().build();
    }
}
