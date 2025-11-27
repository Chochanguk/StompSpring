package test.chat.stompChat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chat_rooms")
public class ChatRoom {

    @Id
    private String id;        // MongoDB 기본 _id

    private Long roomId;      // 프론트에서 사용하는 번호(Long)
    private String name;      // 방 이름
    private LocalDateTime createdAt;
}
