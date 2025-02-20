package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.Model.UserProgress;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends MongoRepository<UserProgress, String> {
    List<UserProgress> findByUserId(String userId);
    Optional<UserProgress> findByUserIdAndCourseId(String userId, String courseId);
}