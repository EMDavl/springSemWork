package ru.itis.services;

import org.springframework.http.ResponseEntity;
import ru.itis.dto.ModeratorDto;
import ru.itis.dto.SignUpDto;

import java.util.List;

public interface AdminService {

    List<ModeratorDto> getModerators();

    ResponseEntity<ModeratorDto> createModerator(SignUpDto credentials);

    ResponseEntity<String> deleteModerator(Long id);
}
