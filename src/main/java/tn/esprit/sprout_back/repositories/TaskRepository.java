package tn.esprit.sprout_back.repositories;

import tn.esprit.sprout_back.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {}