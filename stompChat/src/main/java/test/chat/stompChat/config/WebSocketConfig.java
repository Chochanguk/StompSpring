package test.chat.stompChat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*");
        // ❌ withSockJS() 제거 – 순수 WebSocket만 사용
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 프론트에서 보낼 메시지 prefix: /app/...
        registry.setApplicationDestinationPrefixes("/app");

        // RabbitMQ STOMP Relay
//        registry.enableStompBrokerRelay("/topic", "/queue")
//                .setRelayHost("localhost")
//                .setRelayPort(61613)
//                .setVirtualHost("/")                 // 이거 명시
//                .setSystemLogin("chatuser")
//                .setSystemPasscode("chatpass")
//                .setClientLogin("chatuser")
//                .setClientPasscode("chatpass");

        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setVirtualHost("/")                 // 이거 명시
                .setSystemLogin("guest")
                .setSystemPasscode("guest")
                .setClientLogin("guest")
                .setClientPasscode("guest");

    }
}
