package tn.esprit.sprout_back.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.sprout_back.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    // Trouver un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Trouver un utilisateur par son token de réinitialisation
    Optional<User> findByResetToken(String resetToken);
}