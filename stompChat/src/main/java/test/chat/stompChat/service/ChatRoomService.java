package test.chat.stompChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.chat.stompChat.model.ChatRoom;
import test.chat.stompChat.repository.ChatRoomRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // 방 전체 조회
    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    // 방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom room = ChatRoom.builder()
                .roomId(System.currentTimeMillis())  // 임시 roomId
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
        return chatRoomRepository.save(room);
    }

    // 특정 방 조회
    public ChatRoom findRoom(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    // 방 삭제
    public void deleteRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findByRoomId(roomId);
        if (room != null) {
            chatRoomRepository.delete(room);
        }
    }
}
