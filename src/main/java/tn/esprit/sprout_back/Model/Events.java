package tn.esprit.sprout_back.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Events {
    @Id
    private String id;
    private String title;
    private String description;  // Ajout de la description au lieu des tags
    private LocalDateTime date;
    private String location;
    private int participantsCount;
    private List<String> reviews;
    private String eventType;  // Hackathon, Meetup, Conférence, etc.
    private String organizerId;  // ID de l'utilisateur organisateur
    private String status;  // Prévu, En cours, Terminé
    private String bannerImage;  // URL de l'affiche de l'événement
}
