package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.Model.Application;
import tn.esprit.sprout_back.Model.Project;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByProjectId(String projectId);
}
