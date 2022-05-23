package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.services.ModeratorService;
import ru.itis.services.TaskService;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ModeratorService moderatorService;
    private final TaskService taskService;

    @GetMapping
    public String getPage(Model model) {

        model.addAttribute("tasks", moderatorService.getTenTasks());
        return "tasks";

    }

    @GetMapping("/{taskId}")
    public String taskPage(@PathVariable("taskId") Long taskId, Model model) {

        model.addAttribute("task", taskService.getTaskById(taskId));
        return "taskPage";

    }

    @PostMapping()
    public String takeTask(Authentication auth, @RequestParam("taskId") Long taskId) {

        moderatorService.takeTask(((String) auth.getPrincipal()), taskId);
        return "redirect:/tasks/{taskId}";

    }

    @GetMapping("/my")
    public String getPersonalTasks(Authentication auth, Model model) {

        model.addAttribute("tasks", moderatorService.getPersonalTasks(((String) auth.getPrincipal())));
        return "myTasks";

    }

    @PostMapping("/{taskId}")
    public String verdict(@PathVariable("taskId") Long taskId, @RequestParam("verdict") String verdict, Authentication auth) {
        if (taskService.isReviewer((String) auth.getPrincipal(), taskId)) {
            taskService.verdict(taskId, verdict);
        }
        return "redirect:/tasks";
    }
}
