package ru.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.models.ApproveTask;
import ru.itis.models.Post;
import ru.itis.repositories.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public void createTask(Post post) {

        taskRepository.save(ApproveTask.builder()
                .post(post)
                .build());
    }

    @Override
    public List<ApproveTask> getTenOpenTasks() {
        return taskRepository.findByReviewerIsNull(getTenTasks());
    }

    private Pageable getTenTasks() {
        return PageRequest.of(0, 10);
    }
}
