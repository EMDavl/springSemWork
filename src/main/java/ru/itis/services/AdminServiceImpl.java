package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.ModeratorDto;
import ru.itis.dto.SignUpDto;
import ru.itis.models.User;
import ru.itis.models.enums.Role;
import ru.itis.repositories.TaskRepository;
import ru.itis.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;

    @Override
    public List<ModeratorDto> getModerators() {
        return ModeratorDto.from(repository.findAllModerators());
    }

    @Override
    public ResponseEntity<ModeratorDto> createModerator(SignUpDto credentials) {
        if (repository.existsByEmail(credentials.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                ModeratorDto.from(
                        repository.save(User.builder()
                                .email(credentials.getEmail())
                                .password(passwordEncoder.encode(credentials.getPassword()))
                                .isPublicAccount(false)
                                .nickname(credentials.getNickname())
                                .role(Role.MODERATOR)
                                .status(User.ACCOUNT_STATUS.CONFIRMED)
                                .build()
                        )
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteModerator(Long id) {

        Optional<User> byId = repository.findById(id);
        if (byId.isEmpty() || !byId.get().getRole().equals(Role.MODERATOR)) {
            return ResponseEntity.badRequest().build();
        }

        taskRepository.openModeratorTasks(byId.get());
        repository.delete(byId.get());

        return ResponseEntity.ok().build();
    }
}
