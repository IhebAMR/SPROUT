package tn.esprit.sprout_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sprout_back.Model.EventParticipants;
import tn.esprit.sprout_back.Service.EventParticipantService;

import java.util.List;

@RestController
@RequestMapping("/event-participants")
public class EventParticipantController {
    @Autowired
    private EventParticipantService eventParticipantService;

    @GetMapping
    public ResponseEntity<List<EventParticipants>> getAllParticipants() {
        return ResponseEntity.ok(eventParticipantService.getAllParticipants());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventParticipants>> getParticipantsByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(eventParticipantService.getParticipantsByEventId(eventId));
    }

    @PostMapping
    public ResponseEntity<EventParticipants> registerParticipant(@RequestBody EventParticipants participant) {
        return ResponseEntity.ok(eventParticipantService.registerParticipant(participant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventParticipants> updateParticipant(@PathVariable String id, @RequestBody EventParticipants participant) {
        return ResponseEntity.ok(eventParticipantService.updateParticipant(id, participant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable String id) {
        eventParticipantService.deleteParticipant(id);
        return ResponseEntity.ok("Participant deleted successfully");
    }
}

