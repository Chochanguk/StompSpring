package test.chat.stompChat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthChecker {

    @GetMapping("/api/health")
    public String health() {
        return "Chat Server is running!";
    }

}
