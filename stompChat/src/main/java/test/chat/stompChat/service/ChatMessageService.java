package test.chat.stompChat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import test.chat.stompChat.model.ChatMessage;
import test.chat.stompChat.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void handleIncomingMessage(ChatMessage message) {

        if (message.getType() == null) {
            message.setType(ChatMessage.MessageType.TALK);
        }

        // ✅ ENTER / LEAVE 시스템 메시지를 서버에서 만들어 주기
        switch (message.getType()) {
            case ENTER -> {
                if (isBlank(message.getMessage())) {
                    message.setMessage(message.getSender() + "님이 입장했습니다.");
                }
            }
            case LEAVE -> {
                if (isBlank(message.getMessage())) {
                    message.setMessage(message.getSender() + "님이 퇴장했습니다.");
                }
            }
            case TALK -> {
                // 일반 채팅은 그대로 둠
            }
        }

        message.setSentAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));


        // 1) MongoDB 저장
        ChatMessage saved = chatMessageRepository.save(message);
        log.info("💾 Mongo 저장 완료: {}", saved);

        // 2) 구독자에게 브로드캐스트
        String destination = "/topic/room." + saved.getRoomId();
        messagingTemplate.convertAndSend(destination, saved);
        log.info("📢 브로드캐스트: dest={}, type={}", destination, saved.getType());
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public List<ChatMessage> getHistory(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }


    // ✅ 특정 방(roomId)의 메시지 전체 삭제
    public long deleteAllInRoom(Long roomId) {
        long deletedCount = chatMessageRepository.deleteByRoomId(roomId);
        log.info("🧹 roomId={} 메시지 {}개 삭제", roomId, deletedCount);
        return deletedCount;
    }

    // ✅ 전체 방 메시지 전부 삭제(원하면 사용)
    public void deleteAllMessages() {
        chatMessageRepository.deleteAll();
        log.info("🧨 모든 채팅 메시지 삭제 완료");
    }
}
