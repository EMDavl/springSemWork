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

    @PostMapping()
    public String takeTask(@RequestParam(name = "taskId") String taskId, Authentication auth) {

        moderatorService.takeTask(((String) auth.getPrincipal()), Long.valueOf(taskId));
        return "redirect:/tasks/" + taskId;

    }

    @GetMapping("/{taskId}")
    public String taskPage(@PathVariable("taskId") Long taskId, Model model) {

        model.addAttribute("task", taskService.getTaskById(taskId));
        return "taskPage";

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
