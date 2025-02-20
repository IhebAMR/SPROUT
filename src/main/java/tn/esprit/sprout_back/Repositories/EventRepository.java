package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.sprout_back.Model.Events;

@Repository
public interface EventRepository extends MongoRepository<Events, String> {
}

