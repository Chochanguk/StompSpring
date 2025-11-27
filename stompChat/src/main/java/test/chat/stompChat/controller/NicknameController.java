// NicknameController.java
package test.chat.stompChat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.chat.stompChat.model.ActiveUser;
import test.chat.stompChat.service.NicknameService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nickname")
public class NicknameController {

    private final NicknameService nicknameService;

    /** 닉네임 중복 체크 */
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam String nickname) {
        boolean exists = nicknameService.isDuplicate(nickname);
        return ResponseEntity.ok(exists);  // true = 이미 존재함
    }

    /** 닉네임 저장 */
    @PostMapping("/register")
    public ResponseEntity<ActiveUser> registerNickname(@RequestParam String nickname) {
        ActiveUser saved = nicknameService.registerNickname(nickname);
        return ResponseEntity.ok(saved);
    }

}
