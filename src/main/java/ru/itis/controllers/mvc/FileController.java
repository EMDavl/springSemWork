package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.services.FileService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{storageFileName}")
    public void getFile(@PathVariable("storageFileName") String fileName, HttpServletResponse response) {
        fileService.addFileToResponse(fileName, response);
    }

}
