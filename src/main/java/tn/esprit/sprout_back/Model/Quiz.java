package tn.esprit.sprout_back.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "quizzes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    private String id;
    private String courseId; // References Course
    private String title;
    private String description;
    private List<Question> questions; // Embedded document
    private int passingScore;
    // Getters and setters
}

