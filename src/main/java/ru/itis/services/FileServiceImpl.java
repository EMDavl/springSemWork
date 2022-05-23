package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.exceptions.FileNotFoundException;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    @Value("${storage.path}")
    private String storagePath;
    @Value("${file.load_link}")
    private String filesLink;

    @Override
    public FileInfo uploadFile(MultipartFile file) {

        String fileSystemId = UUID.randomUUID().toString();

        FileInfo fileInfo = FileInfo.builder()
                .loadLink(filesLink + fileSystemId)
                .mimeType(file.getContentType())
                .originalFileName(file.getOriginalFilename())
                .storageFileName(fileSystemId)
                .size(file.getSize())
                .build();

        upload(file, fileInfo.getStorageFileName());
        fileRepository.save(fileInfo);

        return fileInfo;
    }

    @Override
    public void addFileToResponse(String fileName, HttpServletResponse response) {
        FileInfo file = fileRepository.findByStorageFileName(fileName).orElseThrow(FileNotFoundException::new);

        response.setContentLength((int) file.getSize());
        response.setContentType(file.getMimeType());

        response.setHeader("Content-Disposition", "filename=\"" + file.getOriginalFileName() + "\"");

        try (FileInputStream stream = new FileInputStream(storagePath + file.getStorageFileName())) {
            response.getOutputStream().write(stream.readAllBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public void upload(MultipartFile file, String fileSystemName) {
        try {
            Files.copy(file.getInputStream(), Paths.get(storagePath, fileSystemName));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
