// WebConfig.java
package test.chat.stompChat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 윈도우 경로 -> file: 프리픽스 필요
        String location = "file:" + (uploadDir.endsWith("/") ? uploadDir : uploadDir + "/");

        // /files/** 로 들어오는 요청을 uploadDir 로 매핑
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + uploadDir + "/");

    }
}
