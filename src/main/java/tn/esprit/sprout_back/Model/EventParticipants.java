package tn.esprit.sprout_back.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "event_participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipants {
    @Id
    private String id;
    private String eventId;  // Référence vers un Event
    private String userId;  // Référence vers un utilisateur
    private String status;  // Inscrit, Présent, Absent
    private LocalDateTime registrationDate;  // Date d'inscription
    private String feedback;  // Commentaire du participant
    private int rating;  // Note de 1 à 5
    private boolean checkInStatus;  // Présent ou non
}
