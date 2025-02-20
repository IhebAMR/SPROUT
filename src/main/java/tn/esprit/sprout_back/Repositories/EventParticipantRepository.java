package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.sprout_back.Model.EventParticipants;

import java.util.List;

@Repository
public interface EventParticipantRepository extends MongoRepository<EventParticipants, String> {
    List<EventParticipants> findByEventId(String eventId);
}

