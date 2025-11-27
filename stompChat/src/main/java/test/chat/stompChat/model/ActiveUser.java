// ActiveUser.java
package test.chat.stompChat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "active_users")
public class ActiveUser {

    @Id
    private String id;

    private String nickname;
}
