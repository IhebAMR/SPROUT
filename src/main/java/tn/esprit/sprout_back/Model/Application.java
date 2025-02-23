package tn.esprit.sprout_back.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "applications")
@Data
@Getter
@Setter
public class Application {
    @Id
    private String id;

    @DBRef// Charge le projet associé avec la candidature
    private Project project; // Référence au projet

    private String userId;
    private String candidateEmail;
    private ApplicationStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructeur par défaut
    public Application() {}

    // Constructeur avec paramètres
    public Application(Project project, String userId, String candidateEmail, ApplicationStatus status) {
        this.project = project;
        this.userId = userId;
        this.candidateEmail = candidateEmail;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}
