package test.chat.stompChat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Slf4j
@Component
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        log.info("🟢 STOMP CONNECT: {}", event.getMessage().getHeaders());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("🔴 STOMP DISCONNECT: 세션 ID = {}", event.getSessionId());
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        log.info("📩 STOMP SUBSCRIBE: {}", event.getMessage().getHeaders());
    }

    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {
        log.info("📭 STOMP UNSUBSCRIBE: {}", event.getMessage().getHeaders());
    }
}
