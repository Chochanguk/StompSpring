package test.chat.stompChat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import test.chat.stompChat.model.ChatRoom;
import test.chat.stompChat.service.ChatRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public List<ChatRoom> getRooms() {
        return chatRoomService.findAllRooms();
    }

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomService.createRoom(name);
    }

    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable Long roomId) {
        chatRoomService.deleteRoom(roomId);
    }
}
