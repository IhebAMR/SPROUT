package tn.esprit.sprout_back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sprout_back.Model.EventParticipants;
import tn.esprit.sprout_back.Repositories.EventParticipantRepository;

import java.util.List;

@Service
public class EventParticipantService {
    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    public List<EventParticipants> getAllParticipants() {
        return eventParticipantRepository.findAll();
    }

    public List<EventParticipants> getParticipantsByEventId(String eventId) {
        return eventParticipantRepository.findByEventId(eventId);
    }

    public EventParticipants registerParticipant(EventParticipants participant) {
        return eventParticipantRepository.save(participant);
    }

    public EventParticipants updateParticipant(String id, EventParticipants participantDetails) {
        EventParticipants participant = eventParticipantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        participant.setStatus(participantDetails.getStatus());
        participant.setCheckInStatus(participantDetails.isCheckInStatus());
        return eventParticipantRepository.save(participant);
    }

    public void deleteParticipant(String id) {
        eventParticipantRepository.deleteById(id);
    }
}

