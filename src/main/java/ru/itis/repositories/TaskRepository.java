package ru.itis.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.models.ApproveTask;
import ru.itis.models.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<ApproveTask, Long> {

    List<ApproveTask> findByReviewerIsNull(Pageable pageable);

    @Modifying()
    @Query("UPDATE ApproveTask t SET t.reviewer=null WHERE t.reviewer=:user")
    void openModeratorTasks(@Param("user") User user);
}
