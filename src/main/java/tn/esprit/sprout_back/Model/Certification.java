package tn.esprit.sprout_back.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "certifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Certification {
    @Id
    private String id;
    private String courseId; // References Course
    private String userId; // References User (handled by teammates)
    private LocalDate dateAwarded;
    // Getters and setters
}