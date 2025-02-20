package tn.esprit.sprout_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sprout_back.Model.UserProgress;
import tn.esprit.sprout_back.Service.UserProgressService;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    @PutMapping
    public UserProgress updateProgress(@RequestParam String userId, @RequestParam String courseId, @RequestParam double completionPercentage) {
        return userProgressService.updateProgress(userId, courseId, completionPercentage);
    }

    @GetMapping("/user/{userId}")
    public List<UserProgress> getUserProgress(@PathVariable String userId) {
        return userProgressService.getUserProgress(userId);
    }
}