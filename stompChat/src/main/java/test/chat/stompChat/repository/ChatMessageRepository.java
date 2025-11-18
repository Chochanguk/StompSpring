package test.chat.stompChat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.chat.stompChat.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    // 방별 대화 내역 조회
    List<ChatMessage> findByRoomIdOrderBySentAtAsc(Long roomId);

    // ✅ 특정 방(roomId)의 메시지 전체 삭제
    long deleteByRoomId(Long roomId);
}
