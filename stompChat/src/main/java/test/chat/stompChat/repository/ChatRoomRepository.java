package test.chat.stompChat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.chat.stompChat.model.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    ChatRoom findByRoomId(Long roomId);

    boolean existsByRoomId(Long roomId);
}
