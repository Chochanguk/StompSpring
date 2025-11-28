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

    /** 방 목록 조회 */
    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    /** 방 생성 (비밀번호 + 방장) */
    public ChatRoom createRoom(String name, String password, String creatorNickname) {

        ChatRoom newRoom = ChatRoom.builder()
                .roomId(System.currentTimeMillis())
                .name(name)
                .createdAt(LocalDateTime.now())
                .locked(password != null && !password.isBlank())
                .password(password)
                .creatorNickname(creatorNickname)   // 방장 저장
                .build();

        return chatRoomRepository.save(newRoom);
    }

    /** 비밀번호 검증 */
    public boolean verifyPassword(Long roomId, String password) {

        ChatRoom room = chatRoomRepository.findByRoomId(roomId);

        if (room == null) return false;
        if (!room.getLocked()) return true;

        return room.getPassword() != null && room.getPassword().equals(password);
    }

    /** 특정 방 조회 */
    public ChatRoom findRoom(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    /** 방 삭제 (방장 검증 추가) */
    public boolean deleteRoom(Long roomId, String nickname) {

        ChatRoom room = chatRoomRepository.findByRoomId(roomId);
        if (room == null) return false;

        // 방장이 아닌 경우 삭제 불가
        if (!room.getCreatorNickname().equals(nickname)) {
            return false;
        }

        chatRoomRepository.delete(room);
        return true;
    }


    public boolean transferOwner(Long roomId, String from, String to) {

        ChatRoom room = chatRoomRepository.findByRoomId(roomId);

        if (room == null) return false;

        // 요청자가 실제 방장인지 검증
        if (!room.getCreatorNickname().equals(from)) {
            return false;   // 방장이 아니면 위임 불가능
        }

        // 위임 실행
        room.setCreatorNickname(to);
        chatRoomRepository.save(room);

        return true;
    }

}
