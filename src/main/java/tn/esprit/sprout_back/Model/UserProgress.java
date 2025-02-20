package tn.esprit.sprout_back.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user_progress")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserProgress {
    @Id
    private String id;
    private String userId; // References User
    private String courseId; // References Course
    private double completionPercentage;
    private LocalDateTime lastAccessed;
    // Getters and setters
}