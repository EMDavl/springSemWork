package ru.itis.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfo;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    FileInfo uploadFile(MultipartFile file);

    void addFileToResponse(String fileName, HttpServletResponse response);
}
