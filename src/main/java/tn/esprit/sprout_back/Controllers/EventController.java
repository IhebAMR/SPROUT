package tn.esprit.sprout_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sprout_back.Model.Events;
import tn.esprit.sprout_back.Service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Events>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Events> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Events> createEvent(@RequestBody Events event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvent(@PathVariable String id, @RequestBody Events event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }
}

