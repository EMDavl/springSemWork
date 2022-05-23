package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.dto.ApproveTaskDto;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeratorServiceImpl implements ModeratorService {

    private final UsersRepository usersRepository;
    private final TaskService taskService;

    @Override
    public List<ApproveTaskDto> getTenTasks() {
        return taskService.getTenOpenTasks();
    }

    @Override
    public List<ApproveTaskDto> getPersonalTasks(String email) {
        User user = usersRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email <" + email + "> not found"));

        return taskService.findAllByReviewer(user);
    }

    @Override
    public void takeTask(String email, Long taskId) {
        User user = usersRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email <" + email + "> not found"));

        taskService.setReviewer(user, taskId);
    }
}
