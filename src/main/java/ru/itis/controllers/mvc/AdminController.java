package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.CredentialsDto;
import ru.itis.dto.ModeratorDto;
import ru.itis.services.AdminService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String getPage(Model model) {

        model.addAttribute("moderators", adminService.getModerators());

        return "admin";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModeratorDto> createModerator(@RequestBody CredentialsDto credentials) {
        return adminService.createModerator(credentials);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModerator(@PathVariable Long id) {
        return adminService.deleteModerator(id);
    }

}
