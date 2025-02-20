package tn.esprit.sprout_back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.sprout_back.Model.Events;
import tn.esprit.sprout_back.Repositories.EventRepository;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    public Events getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Events createEvent(Events event) {
        return eventRepository.save(event);
    }

    public Events updateEvent(String id, Events eventDetails) {
        Events event = getEventById(id);
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());  // Mise à jour de la description
        event.setDate(eventDetails.getDate());
        event.setLocation(eventDetails.getLocation());
        event.setStatus(eventDetails.getStatus());
        return eventRepository.save(event);
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}

