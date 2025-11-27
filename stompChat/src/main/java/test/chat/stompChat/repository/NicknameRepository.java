// NicknameRepository.java
package test.chat.stompChat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.chat.stompChat.model.ActiveUser;

public interface NicknameRepository extends MongoRepository<ActiveUser, String> {
    boolean existsByNickname(String nickname);
}
