package tn.esprit.sprout_back.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.models.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {
}
