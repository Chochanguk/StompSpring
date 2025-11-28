package test.chat.stompChat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import test.chat.stompChat.model.ChatRoom;
import test.chat.stompChat.service.ChatRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    /** 방 단건 조회 */
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoom> getRoom(@PathVariable Long roomId) {
        ChatRoom room = chatRoomService.findRoom(roomId);
        return ResponseEntity.ok(room);
    }

    /** 방 전체 조회 */
    @GetMapping
    public List<ChatRoom> getRooms() {
        return chatRoomService.findAllRooms();
    }

    /** 방 생성 */
    @PostMapping
    public ResponseEntity<ChatRoom> createRoom(
            @RequestParam String name,
            @RequestParam(required = false) String password,
            @RequestParam String creator   // 프론트에서 닉네임 보내줌
    ) {
        ChatRoom room = chatRoomService.createRoom(name, password, creator);
        return ResponseEntity.ok(room);
    }

    /** 비밀번호 검증 */
    @PostMapping("/{roomId}/verify")
    public ResponseEntity<Boolean> verifyPassword(
            @PathVariable Long roomId,
            @RequestParam String password
    ) {
        return ResponseEntity.ok(chatRoomService.verifyPassword(roomId, password));
    }

    /** 방 삭제 (방장만 가능) */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long roomId,
            @RequestParam String nickname
    ) {

        boolean ok = chatRoomService.deleteRoom(roomId, nickname);
        if (!ok) return ResponseEntity.status(403).build();

        // 🟢 방 삭제 이벤트를 모든 사용자에게 전송
        messagingTemplate.convertAndSend("/topic/rooms", roomId);

        return ResponseEntity.noContent().build();
    }

    /** 방장 위임*/
    @PostMapping("/{roomId}/transfer")
    public ResponseEntity<Boolean> transferOwner(
            @PathVariable Long roomId,
            @RequestParam String from,
            @RequestParam String to
    ) {
        boolean ok = chatRoomService.transferOwner(roomId, from, to);
        return ResponseEntity.ok(ok);
    }

}
