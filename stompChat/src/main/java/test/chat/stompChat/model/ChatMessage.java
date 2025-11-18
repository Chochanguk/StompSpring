package test.chat.stompChat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chat_messages")   // MongoDB 컬렉션 이름
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    @Id
    private String id;            // MongoDB _id

    private MessageType type;
    private Long roomId;
    private String sender;
    private String message;

    private LocalDateTime sentAt; // 보낸 시각 (저장용)


    private String fileUrl;   // 실제 접근 URL
    private String fileName;  // 원본 파일 이름
}
