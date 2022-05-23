package ru.itis.services;

import ru.itis.dto.ApproveTaskDto;

import java.util.List;

public interface ModeratorService {

    List<ApproveTaskDto> getTenTasks();

    List<ApproveTaskDto> getPersonalTasks(String email);

    void takeTask(String email, Long taskId);
}
