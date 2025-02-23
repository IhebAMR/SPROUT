package tn.esprit.sprout_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sprout_back.Model.Application;
import tn.esprit.sprout_back.Model.Project;
import tn.esprit.sprout_back.Repositories.ApplicationRepository;
import tn.esprit.sprout_back.Repositories.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/{projectId}/applications")
    public Application addApplicationToProject(@PathVariable String projectId, @RequestBody Application application) {
        Project project =projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        application.setProject(project);  // Associer la candidature au projet
        return applicationRepository.save(application);
    }

    @GetMapping("/project/{projectId}")
    public List<Application> getApplicationsByProject(@PathVariable String projectId) {
        return applicationRepository.findByProjectId(projectId);
    }
}
