package tn.esprit.sprout_back.repositories;

import tn.esprit.sprout_back.models.CodeSubmission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeSubmissionRepository extends MongoRepository<CodeSubmission, String> {}