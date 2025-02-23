package tn.esprit.sprout_back.Model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "projects")
@Getter
@Setter
public class Project {@Id
private String id;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private List<String> backlog = new ArrayList<>(); // Liste des tâches du backlog

    @DBRef
    private List<Application> applications = new ArrayList<>(); // Candidatures liées au projet

    @CreatedDate
    private LocalDateTime createdAt;


    // Constructeur par défaut
    public Project() {
        this.createdAt = LocalDateTime.now();  // Initialisation de createdAt
    }


    // Constructeur avec paramètres
    public Project(String title, String description, LocalDate startDate, LocalDate endDate, String status) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}
