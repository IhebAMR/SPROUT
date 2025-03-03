package tn.esprit.sprout_back.controllers;


import tn.esprit.sprout_back.models.CodeSubmission;
import tn.esprit.sprout_back.models.Task;
import tn.esprit.sprout_back.services.CodeSubmissionService;
import tn.esprit.sprout_back.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CodeSubmissionService codeSubmissionService;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/tasks/assign/{taskId}")
    public String assignTask(@PathVariable String taskId, @RequestParam String developerId) {
        Task task = taskService.assignTask(taskId, developerId);
        return task != null ? "Task assigned successfully!" : "Task not available.";
    }

    @PostMapping("/code/save")
    public String saveCode(@RequestBody CodeSubmission submission) {
        codeSubmissionService.saveCode(submission);
        return "Code saved successfully!";
    }

    @PostMapping("/code/submit")
    public String submitCode(@RequestBody CodeSubmission submission) {
        codeSubmissionService.submitCode(submission);
        return "Code submitted for review!";
    }
}