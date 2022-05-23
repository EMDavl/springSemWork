package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.ApproveTask;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveTaskDto {

    private Long id;
    private PostDto post;

    public static List<ApproveTaskDto> from(List<ApproveTask> tasks) {
        return tasks.stream().map(ApproveTaskDto::from).collect(Collectors.toList());
    }

    public static ApproveTaskDto from(ApproveTask task) {
        return ApproveTaskDto.builder()
                .id(task.getId())
                .post(PostDto.from(task.getPost()))
                .build();
    }
}
