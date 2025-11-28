// NicknameService.java
package test.chat.stompChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.chat.stompChat.model.ActiveUser;
import test.chat.stompChat.repository.NicknameRepository;

@Service
@RequiredArgsConstructor
public class NicknameService {

    private final NicknameRepository nicknameRepository;

    /** 중복 체크 */
    public boolean isDuplicate(String nickname) {
        return nicknameRepository.existsByNickname(nickname);
    }

    /** 닉네임 저장 */
    public ActiveUser registerNickname(String nickname) {

        if (nickname ==  "null"){
            throw new RuntimeException("null은 닉네임 설정이 안됩니다.");
        }
        // 이미 존재하면 저장 불가
        if (nicknameRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        ActiveUser user = ActiveUser.builder()
                .nickname(nickname)
                .build();

        return nicknameRepository.save(user);
    }
}
