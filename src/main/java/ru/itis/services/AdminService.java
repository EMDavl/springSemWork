package ru.itis.services;

import org.springframework.http.ResponseEntity;
import ru.itis.dto.CredentialsDto;
import ru.itis.dto.ModeratorDto;

import java.util.List;

public interface AdminService {

    List<ModeratorDto> getModerators();

    ResponseEntity<ModeratorDto> createModerator(CredentialsDto credentials);

    ResponseEntity<String> deleteModerator(Long id);
}
