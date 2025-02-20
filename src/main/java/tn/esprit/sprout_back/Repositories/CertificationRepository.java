package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.Model.Certification;

import java.util.List;

public interface CertificationRepository extends MongoRepository<Certification, String> {
    List<Certification> findByUserId(String userId);
}