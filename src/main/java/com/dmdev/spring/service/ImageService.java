package com.dmdev.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket:C:\\Users\\edikk\\IdeaProjects\\spring-starter\\images}")
    private final String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        var fullImagePath = Path.of(bucket, imagePath);

        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        var fullImagePath = Path.of(bucket, imagePath);

        return Files.exists(fullImagePath) // проверяет есть ли по такому пути файл
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty(); // в противном случае вернём пустой экземпляр
    }
}
