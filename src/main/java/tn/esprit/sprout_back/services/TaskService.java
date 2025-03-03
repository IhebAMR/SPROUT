package tn.esprit.sprout_back.services;

import tn.esprit.sprout_back.models.Task;
import tn.esprit.sprout_back.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task assignTask(String taskId, String developerId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if ((task != null) && "Available".equals(task.getStatus())) {
            task.setStatus("In Progress");
            task.setAssignedTo(developerId);
            return taskRepository.save(task);
        }
        return null;
    }
}