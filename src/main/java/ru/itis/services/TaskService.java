package ru.itis.services;

import ru.itis.dto.ApproveTaskDto;
import ru.itis.models.Post;
import ru.itis.models.User;

import java.util.List;

public interface TaskService {

    void createTask(Post post);

    List<ApproveTaskDto> getTenOpenTasks();

    void deleteTaskByPost(Post post);

    List<ApproveTaskDto> findAllByReviewer(User user);

    void verdict(Long taskId, String verdict);

    boolean isReviewer(String email, Long taskId);

    ApproveTaskDto getTaskById(Long taskId);

    void setReviewer(User user, Long taskId);
}
