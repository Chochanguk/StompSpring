// ChatFileController.java
package test.chat.stompChat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test.chat.stompChat.service.FileStorageService;

@RestController
@RequestMapping("/api/chat/files")
@RequiredArgsConstructor
public class ChatFileController {

    private final FileStorageService fileStorageService;

    public record ChatFileResponse(String url, String originalName) {}

    @PostMapping
    public ChatFileResponse upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("roomId") Long roomId
    ) {
        var info = fileStorageService.store(file);
        return new ChatFileResponse(info.url(), info.originalName());
    }

}
