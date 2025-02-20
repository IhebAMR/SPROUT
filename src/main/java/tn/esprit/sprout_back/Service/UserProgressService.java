package tn.esprit.sprout_back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sprout_back.Model.UserProgress;
import tn.esprit.sprout_back.Repositories.UserProgressRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserProgressService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    public UserProgress updateProgress(String userId, String courseId, double completionPercentage) {
        UserProgress progress = userProgressRepository.findByUserIdAndCourseId(userId, courseId)
                .orElse(new UserProgress());
        progress.setUserId(userId);
        progress.setCourseId(courseId);
        progress.setCompletionPercentage(completionPercentage);
        progress.setLastAccessed(LocalDateTime.now());
        return userProgressRepository.save(progress);
    }

    public List<UserProgress> getUserProgress(String userId) {
        return userProgressRepository.findByUserId(userId);
    }
}