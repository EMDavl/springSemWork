package ru.itis.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.ApproveTask;

import java.util.List;

public interface TaskRepository extends JpaRepository<ApproveTask, Long> {

    List<ApproveTask> findByReviewerIsNull(Pageable pageable);

}
