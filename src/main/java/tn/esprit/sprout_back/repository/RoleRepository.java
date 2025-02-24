package tn.esprit.sprout_back.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.models.ERole;
import tn.esprit.sprout_back.models.Role;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
