package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.Model.Quiz;

import java.util.List;

public interface QuizRepository extends MongoRepository<Quiz, String> {
    List<Quiz> findByCourseId(String courseId);
}