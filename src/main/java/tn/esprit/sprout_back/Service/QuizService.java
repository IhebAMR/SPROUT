package tn.esprit.sprout_back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sprout_back.Model.Quiz;
import tn.esprit.sprout_back.Repositories.QuizRepository;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> getQuizzesByCourseId(String courseId) {
        return quizRepository.findByCourseId(courseId);
    }
}