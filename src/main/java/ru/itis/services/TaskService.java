package ru.itis.services;

import ru.itis.models.ApproveTask;
import ru.itis.models.Post;

import java.util.List;

public interface TaskService {

    void createTask(Post post);

    List<ApproveTask> getTenOpenTasks();

}
