package tn.esprit.sprout_back.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.Model.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
}