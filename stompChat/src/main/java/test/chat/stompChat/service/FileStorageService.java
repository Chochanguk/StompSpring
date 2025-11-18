package test.chat.stompChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public StoredFileInfo store(MultipartFile multipartFile) {
        try {
            Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dirPath);

            String originalName = multipartFile.getOriginalFilename();
            String ext = "";
            if (originalName != null && originalName.contains(".")) {
                ext = originalName.substring(originalName.lastIndexOf("."));
            }
            String storedFileName = UUID.randomUUID() + ext;

            Path targetPath = dirPath.resolve(storedFileName);
            Files.copy(multipartFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // ✅ 브라우저에서 접근하는 URL
            String url = "/files/" + storedFileName;

            return new StoredFileInfo(url, originalName, storedFileName);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    public record StoredFileInfo(String url, String originalName, String storedName) {}
}
