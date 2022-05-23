package ru.itis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.ApproveTaskDto;
import ru.itis.exceptions.TaskNotFoundException;
import ru.itis.models.ApproveTask;
import ru.itis.models.Post;
import ru.itis.models.User;
import ru.itis.repositories.PostRepository;
import ru.itis.repositories.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PostRepository postRepository;
    private final UsersService userService;

    @Override
    public void createTask(Post post) {

        taskRepository.save(ApproveTask.builder()
                .post(post)
                .build());
    }

    @Override
    public List<ApproveTaskDto> getTenOpenTasks() {
        return ApproveTaskDto.from(taskRepository.findByReviewerIsNull(getTenTasks()));
    }

    @Override
    public void deleteTaskByPost(Post post) {
        taskRepository.deleteByPost(post);
    }

    @Override
    public List<ApproveTaskDto> findAllByReviewer(User user) {
        return ApproveTaskDto.from(taskRepository.findAllByReviewer(user));
    }

    @Override
    @Transactional
    public void verdict(Long taskId, String verdict) {

        switch (verdict) {
            case "approved":
                approveTask(taskId);
                break;

            case "declined":
                declineTask(taskId);
                break;
        }

    }

    private void approveTask(Long taskId) {
        ApproveTask approveTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        approveTask.getPost().setStatus(Post.PostStatus.APPROVED);
        postRepository.save(approveTask.getPost());
        approveTask.setPost(null);
        approveTask.setReviewer(null);
        taskRepository.delete(approveTask);
    }

    private void declineTask(Long taskId) {
        ApproveTask approveTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        userService.notifyPostWasDeclined(approveTask.getPost());
        approveTask.setReviewer(null);
        taskRepository.save(approveTask);
    }

    @Override
    public boolean isReviewer(String email, Long taskId) {
        ApproveTask approveTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return approveTask.getReviewer().getEmail().equals(email);
    }

    @Override
    public ApproveTaskDto getTaskById(Long taskId) {
        return ApproveTaskDto.from(taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId)));
    }

    @Override
    public void setReviewer(User user, Long taskId) {
        ApproveTask approveTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        approveTask.setReviewer(user);
        taskRepository.save(approveTask);
    }

    private Pageable getTenTasks() {
        return PageRequest.of(0, 10);
    }
}
