package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.Model.Application;
import tn.esprit.sprout_back.Model.Project;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
